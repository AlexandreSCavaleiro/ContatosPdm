package br.edu.scl.ifsp.ads.contatospdm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.scl.ifsp.ads.contatospdm.Controller.ContactRtDbFbController
import br.edu.scl.ifsp.ads.contatospdm.R
import br.edu.scl.ifsp.ads.contatospdm.adapter.ContactAdapter
import br.edu.scl.ifsp.ads.contatospdm.controller.ContactRoomController
import br.edu.scl.ifsp.ads.contatospdm.databinding.ActivityMainBinding
import br.edu.scl.ifsp.ads.contatospdm.model.Constant.CONTACT_ARRAY
import br.edu.scl.ifsp.ads.contatospdm.model.Constant.EXTRA_CONTACT
import br.edu.scl.ifsp.ads.contatospdm.model.Constant.VIEW_CONTACT
import br.edu.scl.ifsp.ads.contatospdm.model.Contact

class MainActivity : AppCompatActivity() {
    //ViewBinding
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //Data Source
    private val contactList: MutableList<Contact> = mutableListOf()


    // Controller
    private val contactController: ContactRtDbFbController by lazy {
        ContactRtDbFbController(this)
    }
    //Adapter
    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(
            this,
            contactList
        )
    }

    companion object{
        const val GET_CONTACTS_MSG = 1
        const val GET_CONTACTS_INTERVAL = 2000L
    }

    //Handler
    val updateContactListHandler = object: Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            //BUSCA CONTTS OU ATUALIZA LISTA CONFORME A CONST NA MSG
            if(msg.what== GET_CONTACTS_MSG){
                //Busca contts de acordo com o interv e agenda nova busca
                contactController.getContacts()
                sendMessageDelayed(
                    obtainMessage().apply { what = GET_CONTACTS_MSG },
                    GET_CONTACTS_INTERVAL
                )
            } else {
                msg.data.getParcelableArray(CONTACT_ARRAY)?.also {contactArray ->
                    contactList.clear()
                    contactArray.forEach{
                        contactList.add(it as Contact)
                    }
                    contactAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    //ARL
    private lateinit var carl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarIn.toolbar)
        //fillContacts()
        amb.contatoslv.adapter=contactAdapter

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){result ->
            if (result.resultCode == RESULT_OK){
                val contact = result.data?.getParcelableExtra<Contact>(EXTRA_CONTACT)
                contact?.let { _contact ->
                    if(contactList.any { it.id == contact.id }){
                        contactController.editContact(_contact)
                    }else {
                        contactController.insertContact(_contact)
                    }
                }
            }
        }

        amb.contatoslv.setOnItemClickListener{parent,view, position,id->
            val contact = contactList[position]
            val viewContactIntent = Intent(this, ContactActivity::class.java)
                .putExtra(EXTRA_CONTACT, contact)
                .putExtra(VIEW_CONTACT,true)

            startActivity(viewContactIntent)
        }

        registerForContextMenu(amb.contatoslv)
        updateContactListHandler.apply {
            sendMessageDelayed(
                obtainMessage().apply { what = GET_CONTACTS_MSG },
                GET_CONTACTS_INTERVAL
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addContactMi -> {
                carl.launch(Intent(this,ContactActivity::class.java))
                true
            }
            else -> true
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterContextMenuInfo).position
        val contact = contactList[position]

        return when (item.itemId){
            R.id.removeContactMi -> {
                contactController.removeContact(contact)
                Toast.makeText(this,"Removido", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.editContactMo -> {
                val contactToEdit = contactList[position]
                val editContactIntent = Intent(this, ContactActivity::class.java)
                editContactIntent.putExtra(EXTRA_CONTACT, contactToEdit)
                carl.launch(editContactIntent)
                true
            }
            else -> {true}
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterForContextMenu(amb.contatoslv)
    }

}
package br.edu.scl.ifsp.ads.contatospdm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.scl.ifsp.ads.contatospdm.R
import br.edu.scl.ifsp.ads.contatospdm.adapter.ContactAdapter
import br.edu.scl.ifsp.ads.contatospdm.databinding.ActivityMainBinding
import br.edu.scl.ifsp.ads.contatospdm.model.Constant.EXTRA_CONTACT
import br.edu.scl.ifsp.ads.contatospdm.model.Contact

class MainActivity : AppCompatActivity() {
    //ViewBinding
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    //Data Source
    private val contactList: MutableList<Contact> = mutableListOf()

    //Adapter
    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(
            this,
            contactList
        )
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
                val contact =result.data?.getParcelableExtra<Contact>(EXTRA_CONTACT)
                contact?.let { _contact ->
                    contactList.add(_contact)
                    contactAdapter.notifyDataSetChanged()
                }
            }
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

    //função será deletada futuramente
    //é somente uma função para trazer contatos para a lista msm
    /*private fun fillContacts(){
        for (i in 1..50){
            contactList.add(
                Contact(
                    i,
                    "Nome $i",
                    "endereço $i",
                    "99$i",
                    "email$i@gmail.com"
                )
            )
        }
    }*/
}
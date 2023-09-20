package br.edu.scl.ifsp.ads.contatospdm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import br.edu.scl.ifsp.ads.contatospdm.R
import br.edu.scl.ifsp.ads.contatospdm.databinding.ActivityMainBinding
import br.edu.scl.ifsp.ads.contatospdm.model.Contact

class MainActivity : AppCompatActivity() {
    //View
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    //Data Source
    private val contactList: MutableList<Contact> = mutableListOf()

    //Adapter
    private val contactAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter(this,
            android.R.layout.simple_list_item_1,
            contactList.map {contact ->
                contact.name
            }
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        fillContacts()
        amb.contatoslv.adapter=contactAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addContactMi -> {
                //abrir tela ContactActivity p/ add novo contato
                true
            }
            else -> true
        }
    }

    //função será deletada futuramente
    //é somente uma função para trazer contatos para a lista msm
    private fun fillContacts(){
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
    }

}
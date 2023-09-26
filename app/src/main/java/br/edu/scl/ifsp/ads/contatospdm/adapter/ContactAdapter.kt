package br.edu.scl.ifsp.ads.contatospdm.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.scl.ifsp.ads.contatospdm.R
import br.edu.scl.ifsp.ads.contatospdm.model.Contact

class ContactAdapter(context: Context, private val contactList: MutableList<Contact>
): ArrayAdapter<Contact>(context, R.layout.tile_contact, contactList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contact = contactList[position]

        var contactTileView = convertView
        if(contactTileView == null){
            contactTileView = (context.getSystemService(LAYOUT_INFLATER_SERVICE) as
                    LayoutInflater).inflate(R.layout.tile_contact, parent, false)
        }

        contactTileView!!.findViewById<TextView>(R.id.nameTV).setText(contact.name)
        contactTileView!!.findViewById<TextView>(R.id.emailTV).setText(contact.email)


        return contactTileView
    }
}
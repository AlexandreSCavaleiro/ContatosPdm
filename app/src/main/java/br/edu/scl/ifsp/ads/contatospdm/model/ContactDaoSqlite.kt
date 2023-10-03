package br.edu.scl.ifsp.ads.contatospdm.model

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.edu.scl.ifsp.ads.contatospdm.R
import java.sql.SQLException

class ContactDaoSqlite(context: Context):ContactDao {
    companion object Contant{
        private const val CONTACT_DATABASE_FILE = "contacts"
        private const val CONTACT_TABLE = "contact"
        private const val ID_COLUMN = "id"
        private const val NAME_COLUMN = "name"
        private const val ADDRESS_COLUMN = "address"
        private const val PHONE_COLUMN = "phone"
        private const val EMAIL_COLUMN = "email"
        private const val CREATE_CONTACT_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $CONTACT_TABLE (" +
                    "$ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NAME_COLUMN TEXT NOT NULL, " +
                    "$ADDRESS_COLUMN TEXT NOT NULL, " +
                    "$PHONE_COLUMN TEXT NOT NULL, " +
                    "$EMAIL_COLUMN TEXT NOT NULL " +
                    ");"
    }

    private val contactsSqliteDatabase: SQLiteDatabase
    init {
        contactsSqliteDatabase =
            context.openOrCreateDatabase(CONTACT_DATABASE_FILE, MODE_PRIVATE, null)
        try {
            contactsSqliteDatabase.execSQL(CREATE_CONTACT_TABLE_STATEMENT)
        }catch (se: SQLException){
            Log.e(context.getString(R.string.app_name), se.message.toString())
        }
    }


    override fun createContact(contact: Contact): Int {
        val cv = ContentValues()
        cv.put(NAME_COLUMN, contact.name)
        cv.put(ADDRESS_COLUMN,contact.address)
        cv.put(PHONE_COLUMN,contact.phone)
        cv.put(EMAIL_COLUMN,contact.email)

        return contactsSqliteDatabase.insert(CONTACT_TABLE, null,cv).toInt()
    }

    override fun retrieveContact(id: Int): Contact {
        TODO("Not yet implemented")
    }

    override fun retrieveContacts(): MutableList<Contact> {
        TODO("Not yet implemented")
    }

    override fun updateContact(contact: Contact): Int {
        TODO("Not yet implemented")
    }

    override fun deleteContact(id: Int): Int {
        TODO("Not yet implemented")
    }
}
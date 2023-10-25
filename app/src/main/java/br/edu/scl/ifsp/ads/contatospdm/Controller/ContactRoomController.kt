package br.edu.scl.ifsp.ads.contatospdm.Controller

import androidx.room.Room
import br.edu.scl.ifsp.ads.contatospdm.model.Contact
import br.edu.scl.ifsp.ads.contatospdm.model.ContactRoomDao
import br.edu.scl.ifsp.ads.contatospdm.model.ContactRoomDao.Constant.CONTACT_DATABASE_FILE
import br.edu.scl.ifsp.ads.contatospdm.model.ContactRoomDaoDatabase
import br.edu.scl.ifsp.ads.contatospdm.view.MainActivity

class ContactRoomController(mainActivity: MainActivity) {
    private val contactDaoImpl: ContactRoomDao by lazy {
        Room.databaseBuilder(
            mainActivity,
            ContactRoomDaoDatabase::class.java,
            CONTACT_DATABASE_FILE
        ).build().getContactRoomDao()
    }

    fun insertContact(contact: Contact): Int = contactDaoImpl.createContact(contact)
    fun getContact(id: Int) = contactDaoImpl.retrieveContact(id)
    fun getContacts() = contactDaoImpl.retrieveContacts()
    fun editContact(contact: Contact) = contactDaoImpl.updateContact(contact)
    fun removeContact(contact: Contact) = contactDaoImpl.deleteContact(contact)
}
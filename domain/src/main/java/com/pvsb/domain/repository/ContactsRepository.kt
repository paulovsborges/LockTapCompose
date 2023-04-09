package com.pvsb.domain.repository

import com.pvsb.domain.entity.Contact

interface ContactsRepository {
    suspend fun addContact(contact: Contact)
    suspend fun getContacts(): List<Contact>
    suspend fun deleteContact(contactId: Int)
}

package com.pvsb.domain.repository

import com.pvsb.domain.entity.PrivateContact

interface ContactsRepository {
    suspend fun addContact(contact: PrivateContact)
    suspend fun getContacts(): List<PrivateContact>
}

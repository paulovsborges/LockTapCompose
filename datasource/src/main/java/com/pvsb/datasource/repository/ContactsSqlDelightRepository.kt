package com.pvsb.datasource.repository

import com.pvsb.datasource.mapper.privateContact.PrivateContactMapper.toEntity
import com.pvsb.datasource.mapper.privateContact.PrivateContactMapper.toModel
import com.pvsb.domain.entity.PrivateContact
import com.pvsb.domain.repository.ContactsRepository
import com.pvsb.locktapcompose.LockTapDataBase

class ContactsSqlDelightRepository(
    private val db: LockTapDataBase
) : ContactsRepository {

    private val queries = db.privateContactEntityQueries

    override suspend fun addContact(contact: PrivateContact) {
        queries.insertOrReplaceContact(contact.toEntity())
    }

    override suspend fun getContacts(): List<PrivateContact> {
        return queries.getAllContacts().executeAsList().map { it.toModel() }
    }

    override suspend fun deleteContact(contactId: Int) {
        queries.deleteContact(contactId.toLong())
    }
}
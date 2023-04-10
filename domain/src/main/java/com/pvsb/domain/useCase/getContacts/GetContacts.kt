package com.pvsb.domain.useCase.getContacts

import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.repository.ContactsRepository

class GetContacts(
    private val contactsRepository: ContactsRepository
) : GetContactsUseCase {

    override suspend fun invoke(): DataState<List<Contact>> {
        return try {
            val contacts = contactsRepository.getContacts()
            DataState.Success(contacts)
        } catch (e: Exception) {
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}

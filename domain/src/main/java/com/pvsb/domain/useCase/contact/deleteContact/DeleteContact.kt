package com.pvsb.domain.useCase.contact.deleteContact

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.repository.ContactsRepository

class DeleteContact(
    private val contactsRepository: ContactsRepository
) : DeleteContactUseCase {

    override suspend fun invoke(contactId: Int): DataState<Unit> {
        return try {
            contactsRepository.deleteContact(contactId)
            DataState.Success(Unit)
        } catch (e: Exception) {
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}

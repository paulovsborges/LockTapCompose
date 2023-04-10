package com.pvsb.domain.useCase.addContact

import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AddContact(
    private val contactsRepository: ContactsRepository
) : AddContactUseCase {

    sealed interface Error : ExceptionWrapper {
        object ContactAlreadyExists : Error
    }

    override suspend fun invoke(
        input: AddContactUseCase.Input
    ): Flow<DataState<Unit>> {
        val stream = flow {
            val contacts = contactsRepository.getContacts()

            val contactAlreadyExists = checkIfTheContactAlreadyExists(
                input.contactData.phoneNumber, contacts
            )

            if (contactAlreadyExists && input.contactData.contactId.isBlank()) {
                emit(DataState.Error(Error.ContactAlreadyExists))
                return@flow
            }

            val contactId = input.contactData.contactId.ifEmpty {
                "${contacts.size}"
            }

            val contact = input.contactData.copy(contactId = contactId)

            contactsRepository.addContact(contact)

            emit(DataState.Success(Unit))
        }.catch {
            emit(DataState.Error(ExceptionWrapper.Unknown))
        }

        return stream
    }

    private fun checkIfTheContactAlreadyExists(
        contactNumber: String,
        contacts: List<Contact>
    ): Boolean {
        return contacts.map {
            it.phoneNumber
        }.find {
            it == contactNumber
        } != null
    }
}

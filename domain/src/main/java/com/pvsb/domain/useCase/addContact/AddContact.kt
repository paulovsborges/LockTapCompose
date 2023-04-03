package com.pvsb.domain.useCase.addContact

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.PrivateContact
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AddContact(
    private val contactsRepository: ContactsRepository
) : AddContactUseCase {

    override suspend fun invoke(
        input: AddContactUseCase.Input
    ): Flow<DataState> {
        val stream = flow<DataState> {
            val contacts = contactsRepository.getContacts()

            val contact = input.contactData.copy(contactId = "${contacts.size}")

            checkIfTheContactAlreadyExists(
                input.contactData.phoneNumber,
                contacts
            )

            contactsRepository.addContact(contact)

            emit(DataState.Success(Unit))
        }.catch {
            when (it) {
                is ExceptionWrapper.ContactAlreadyExists -> {
                    emit(DataState.Error(TypedMessage.StringMessage(it.message ?: "")))
                }
                else -> {
                    emit(
                        DataState.Error(
                            TypedMessage.StringMessage(
                                "There was an unexpected error"
                            )
                        )
                    )
                }
            }
        }

        return stream
    }

    private fun checkIfTheContactAlreadyExists(
        contactNumber: String,
        contacts: List<PrivateContact>
    ) {
        contacts.map {
            it.phoneNumber
        }.find {
            it == contactNumber
        }?.let {
            throw ExceptionWrapper.ContactAlreadyExists(
                "The contact already exists"
            )
        }
    }
}

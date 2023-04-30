package com.pvsb.domain.useCase.contact.getFavorites

import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.repository.ContactsRepository

class GetFavoriteContacts(
    private val contactsRepository: ContactsRepository
) : GetFavoriteContactsUseCase {

    override suspend fun invoke(): DataState<List<Contact>> {

        return try {
            val contacts = contactsRepository.getContacts()
            val favoritesContacts = contacts.filter { it.isFavorite }

            DataState.Success(favoritesContacts)
        } catch (e: Exception) {
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}
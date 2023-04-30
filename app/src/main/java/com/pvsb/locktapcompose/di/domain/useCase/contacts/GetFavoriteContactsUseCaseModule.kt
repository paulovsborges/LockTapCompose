package com.pvsb.locktapcompose.di.domain.useCase.contacts

import com.pvsb.domain.repository.ContactsRepository
import com.pvsb.domain.useCase.contact.getFavorites.GetFavoriteContacts
import com.pvsb.domain.useCase.contact.getFavorites.GetFavoriteContactsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object GetFavoriteContactsUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        contactsRepository: ContactsRepository
    ): GetFavoriteContactsUseCase {
        return GetFavoriteContacts(contactsRepository)
    }
}

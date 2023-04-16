package com.pvsb.locktapcompose.di.domain.contacts

import com.pvsb.domain.repository.ContactsRepository
import com.pvsb.domain.useCase.contact.getContacts.GetContacts
import com.pvsb.domain.useCase.contact.getContacts.GetContactsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object GetContactsUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        contactsRepository: ContactsRepository
    ): GetContactsUseCase {
        return GetContacts(contactsRepository)
    }
}

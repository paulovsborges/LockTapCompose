package com.pvsb.locktapcompose.di.domain.useCase.contacts

import com.pvsb.domain.repository.ContactsRepository
import com.pvsb.domain.useCase.contact.addContact.AddContact
import com.pvsb.domain.useCase.contact.addContact.AddContactUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AddContactUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        contactsRepository: ContactsRepository
    ): AddContactUseCase {
        return AddContact(contactsRepository)
    }
}

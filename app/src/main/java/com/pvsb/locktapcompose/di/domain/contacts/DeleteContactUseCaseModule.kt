package com.pvsb.locktapcompose.di.domain.contacts

import com.pvsb.domain.repository.ContactsRepository
import com.pvsb.domain.useCase.contact.deleteContact.DeleteContact
import com.pvsb.domain.useCase.contact.deleteContact.DeleteContactUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DeleteContactUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        contactsRepository: ContactsRepository
    ): DeleteContactUseCase {
        return DeleteContact(contactsRepository)
    }
}

package com.pvsb.domain.useCase.getContacts

import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.DataState

interface GetContactsUseCase {
    suspend operator fun invoke(): DataState<List<Contact>>
}
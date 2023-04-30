package com.pvsb.domain.useCase.contact.getFavorites

import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.DataState

interface GetFavoriteContactsUseCase {
    suspend operator fun invoke(): DataState<List<Contact>>
}
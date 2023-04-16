package com.pvsb.domain.useCase.contact.deleteContact

import com.pvsb.domain.entity.DataState

interface DeleteContactUseCase {
    suspend operator fun invoke(contactId: Int): DataState<Unit>
}

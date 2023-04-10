package com.pvsb.domain.useCase.deleteContact

import com.pvsb.domain.entity.DataState

interface DeleteContactUseCase {
    suspend operator fun invoke(contactId: Int): DataState<Unit>
}

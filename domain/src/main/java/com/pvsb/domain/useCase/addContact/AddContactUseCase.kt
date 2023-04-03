package com.pvsb.domain.useCase.addContact

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.PrivateContact
import kotlinx.coroutines.flow.Flow

interface AddContactUseCase {
    suspend operator fun invoke(input: Input): Flow<DataState>

    data class Input(
        val contactData: PrivateContact
    )
}

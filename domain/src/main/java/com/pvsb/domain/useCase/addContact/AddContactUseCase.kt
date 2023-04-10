package com.pvsb.domain.useCase.addContact

import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.DataState
import kotlinx.coroutines.flow.Flow

interface AddContactUseCase {
    suspend operator fun invoke(input: Input): Flow<DataState<Unit>>

    data class Input(
        val contactData: Contact
    )
}

package com.pvsb.domain.useCase.password.addPassword

import com.pvsb.domain.entity.DataState

interface AddPasswordUseCase {

    data class Input(
        val title: String,
        val password: String,
        val additionalInfo: String?
    )

    suspend operator fun invoke(input: Input): DataState<Unit>
}
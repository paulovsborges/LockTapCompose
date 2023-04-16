package com.pvsb.domain.useCase.password.addPassword

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Password

interface AddPasswordUseCase {

    data class Input(
        val title: String,
        val password: String,
        val additionalInfo: String?
    )

    suspend operator fun invoke(input: Input): DataState<Unit>
    suspend operator fun invoke(password: Password): DataState<Unit>
}
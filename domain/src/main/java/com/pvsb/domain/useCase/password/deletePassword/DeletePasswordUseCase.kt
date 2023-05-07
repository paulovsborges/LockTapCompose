package com.pvsb.domain.useCase.password.deletePassword

import com.pvsb.domain.entity.DataState

interface DeletePasswordUseCase {
    suspend operator fun invoke(passwordId: String): DataState<Unit>
}
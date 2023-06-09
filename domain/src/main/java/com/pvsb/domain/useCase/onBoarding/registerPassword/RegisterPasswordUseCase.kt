package com.pvsb.domain.useCase.onBoarding.registerPassword

import com.pvsb.domain.entity.DataState

interface RegisterPasswordUseCase {
    suspend operator fun invoke(
        newPassword: String
    ): DataState<Unit>
}

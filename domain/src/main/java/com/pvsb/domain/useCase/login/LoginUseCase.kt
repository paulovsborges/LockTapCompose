package com.pvsb.domain.useCase.login

import com.pvsb.domain.entity.DataState

interface LoginUseCase {
    suspend operator fun invoke(password: String): DataState<Unit>
}

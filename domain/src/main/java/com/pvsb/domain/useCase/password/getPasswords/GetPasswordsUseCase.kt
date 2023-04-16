package com.pvsb.domain.useCase.password.getPasswords

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Password

interface GetPasswordsUseCase {
    suspend operator fun invoke(): DataState<List<Password>>
}
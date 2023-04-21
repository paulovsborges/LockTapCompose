package com.pvsb.domain.useCase.password.getPasswords

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Password
import kotlinx.coroutines.flow.Flow

interface GetPasswordsUseCase {
    suspend operator fun invoke(): DataState<Flow<List<Password>>>
}

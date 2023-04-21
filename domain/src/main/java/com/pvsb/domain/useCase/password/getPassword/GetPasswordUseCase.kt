package com.pvsb.domain.useCase.password.getPassword

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Password

interface GetPasswordUseCase {

    suspend operator fun invoke(passwordId: String): DataState<Password>
}

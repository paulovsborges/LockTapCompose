package com.pvsb.domain.useCase.password.getPasswords

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.repository.PasswordsRepository

class GetPasswords(
    private val passwordsRepository: PasswordsRepository
) : GetPasswordsUseCase {

    override suspend fun invoke(): DataState<List<Password>> {
        return try {
            DataState.Success(passwordsRepository.getAll())
        } catch (e: Exception) {
            e.printStackTrace()
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}
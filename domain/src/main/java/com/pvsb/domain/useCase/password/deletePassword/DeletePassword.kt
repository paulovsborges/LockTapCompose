package com.pvsb.domain.useCase.password.deletePassword

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.repository.PasswordsRepository

class DeletePassword(
    private val passwordsRepository: PasswordsRepository
) : DeletePasswordUseCase {

    override suspend fun invoke(passwordId: String): DataState<Unit> {
        return try {
            passwordsRepository.delete(passwordId.toInt())
            DataState.Success(Unit)
        } catch (e: Exception) {
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}

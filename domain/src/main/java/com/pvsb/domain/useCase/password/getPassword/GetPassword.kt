package com.pvsb.domain.useCase.password.getPassword

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.repository.PasswordsRepository

class GetPassword(
    private val passwordsRepository: PasswordsRepository
) : GetPasswordUseCase {

    override suspend fun invoke(passwordId: String): DataState<Password> {
        return try {

            val password = passwordsRepository.getPassword(
                passwordId.toInt()
            ) ?: throw NullPointerException("There is no password for id $passwordId on bd")

            DataState.Success(password)
        } catch (e: Exception) {
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}

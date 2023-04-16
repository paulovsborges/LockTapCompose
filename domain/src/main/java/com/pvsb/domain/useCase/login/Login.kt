package com.pvsb.domain.useCase.login

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.repository.UserRepository

class Login(
    private val userRepository: UserRepository
) : LoginUseCase {

    enum class Error : ExceptionWrapper {
        INVALID_PASSWORD
    }

    override suspend fun invoke(password: String): DataState<Unit> {
        return try {
            val userData = userRepository.read() ?: throw NullPointerException(
                "No user data on repository"
            )

            if (userData.password == password) {
                DataState.Success(Unit)
            } else {
                DataState.Error(Error.INVALID_PASSWORD)
            }
        } catch (e: Exception) {
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}

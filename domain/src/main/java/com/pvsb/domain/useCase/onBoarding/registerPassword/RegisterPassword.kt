package com.pvsb.domain.useCase.onBoarding.registerPassword

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.repository.UserRepository
import com.pvsb.domain.util.Logger

class RegisterPassword(
    private val userRepository: UserRepository,
    private val logger: Logger
) : RegisterPasswordUseCase {

    enum class Error : ExceptionWrapper {
        INVALID_PASSWORD
    }

    override suspend fun invoke(
        newPassword: String
    ): DataState<Unit> {

        try {

            val userData = userRepository.read() ?: throw NullPointerException(
                "No user data on repository"
            )

            if (userData.password.isNotEmpty() && newPassword != userData.password) {
                return DataState.Error(Error.INVALID_PASSWORD)
            }

            val updatedData = userData.copy(password = newPassword)

            userRepository.save(updatedData)

            return DataState.Success(Unit)
        } catch (e: Exception) {
            logger.e(e)
            return DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}

package com.pvsb.domain.useCase.getUserData

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.User
import com.pvsb.domain.repository.UserRepository

class GetUserData(
    private val userRepository: UserRepository
) : GetUserDataUseCase {

    override suspend fun invoke(): DataState<User> {
        return try {

            val userData = userRepository.read() ?: throw NullPointerException(
                "No user data on repository"
            )

            DataState.Success(userData)
        } catch (e: Exception) {
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}

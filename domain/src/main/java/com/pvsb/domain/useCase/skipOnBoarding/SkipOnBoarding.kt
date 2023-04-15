package com.pvsb.domain.useCase.skipOnBoarding

import com.pvsb.domain.entity.User
import com.pvsb.domain.repository.UserRepository

class SkipOnBoarding(
    private val userRepository: UserRepository
) : SkipOnBoardingUseCase {

    override suspend fun invoke() {
        try {

            val currentData = userRepository.read() ?: createUserData()

            if (currentData.hasSeenOnBoardingAlready) return

            userRepository.save(
                currentData.copy(
                    hasSeenOnBoardingAlready = true
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createUserData(): User {
        return User("", false)
    }
}
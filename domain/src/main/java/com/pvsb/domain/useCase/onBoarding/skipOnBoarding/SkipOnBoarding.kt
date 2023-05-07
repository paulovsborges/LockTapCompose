package com.pvsb.domain.useCase.onBoarding.skipOnBoarding

import com.pvsb.domain.entity.User
import com.pvsb.domain.repository.UserRepository
import com.pvsb.domain.util.Logger

class SkipOnBoarding(
    private val userRepository: UserRepository,
    private val logger: Logger
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
            logger.e(e)
        }
    }

    private fun createUserData(): User {
        return User("", false)
    }
}

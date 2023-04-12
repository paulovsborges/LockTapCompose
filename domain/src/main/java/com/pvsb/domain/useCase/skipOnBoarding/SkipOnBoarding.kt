package com.pvsb.domain.useCase.skipOnBoarding

import com.pvsb.domain.repository.UserRepository

class SkipOnBoarding(
    private val userRepository: UserRepository
) : SkipOnBoardingUseCase {

    override suspend fun invoke() {
        try {

            val currentData = userRepository.read()

            if (currentData.hasSeenOnBoardingAlready) return

            userRepository.save(
                currentData.copy(
                    password = "",
                    hasSeenOnBoardingAlready = true
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
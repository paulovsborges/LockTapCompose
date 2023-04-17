package com.pvsb.domain.useCase.password.togglePasswordFavorite

import com.pvsb.domain.repository.PasswordsRepository

class TogglePasswordFavorite(
    private val passwordsRepository: PasswordsRepository
) : TogglePasswordFavoriteUseCase {

    override suspend fun invoke(passwordId: String) {
        try {
            val password = passwordsRepository.getPassword(
                passwordId.toInt()
            ) ?: throw NullPointerException("There is no password for id $passwordId on repository")

            val updatedPassword = password.copy(isFavorite = !password.isFavorite)

            passwordsRepository.add(updatedPassword)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
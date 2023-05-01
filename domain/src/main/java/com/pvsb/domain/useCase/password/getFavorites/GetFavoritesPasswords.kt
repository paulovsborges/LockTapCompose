package com.pvsb.domain.useCase.password.getFavorites

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.repository.PasswordsRepository

class GetFavoritesPasswords(
    private val passwordsRepository: PasswordsRepository
) : GetFavoritesPasswordsUseCase {

    override suspend fun invoke(): DataState<List<Password>> {
        return try {
            val passwords = passwordsRepository.getAll()
            val favorites = passwords.filter { it.isFavorite }

            DataState.Success(favorites)
        } catch (e: Exception) {
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}
package com.pvsb.domain.useCase.password.togglePasswordFavorite

interface TogglePasswordFavoriteUseCase {
    suspend operator fun invoke(passwordId: String)
}

package com.pvsb.domain.useCase.password.getFavorites

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Password

interface GetFavoritesPasswordsUseCase {
    suspend operator fun invoke(): DataState<List<Password>>
}
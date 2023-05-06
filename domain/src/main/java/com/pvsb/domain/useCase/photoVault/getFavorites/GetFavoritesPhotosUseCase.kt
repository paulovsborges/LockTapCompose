package com.pvsb.domain.useCase.photoVault.getFavorites

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Photo

interface GetFavoritesPhotosUseCase {
    suspend operator fun invoke(): DataState<List<Photo>>
}
package com.pvsb.domain.useCase.photoVault.togglePhotoFavorite

import com.pvsb.domain.entity.DataState

interface TogglePhotoFavoriteUseCase {
    suspend operator fun invoke(photoId: Long): DataState<Unit>
}
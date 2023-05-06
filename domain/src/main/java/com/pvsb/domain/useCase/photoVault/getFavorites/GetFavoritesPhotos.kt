package com.pvsb.domain.useCase.photoVault.getFavorites

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Photo
import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.useCase.password.getFavorites.GetFavoritesPasswordsUseCase

class GetFavoritesPhotos(
    private val photoVaultRepository: PhotoVaultRepository
) : GetFavoritesPhotosUseCase {

    override suspend fun invoke(): DataState<List<Photo>> {
        return try {
            val photos = photoVaultRepository.getAllPhotos()
            val favorites = photos.filter { it.isFavorite }

            DataState.Success(favorites)
        } catch (e: Exception) {
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}
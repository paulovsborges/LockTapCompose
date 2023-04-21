package com.pvsb.domain.useCase.photoVault.togglePhotoFavorite

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.repository.PhotoVaultRepository

class TogglePhotoFavorite(
    private val repository: PhotoVaultRepository
) : TogglePhotoFavoriteUseCase {

    override suspend fun invoke(photoId: Long): DataState<Unit> {
        return try {

            val photo = repository.getPhotoById(photoId) ?: throw NullPointerException(
                "No photo for id $photoId on vault"
            )

            val updatedPhoto = photo.copy(isFavorite = !photo.isFavorite)

            repository.addOrReplacePhoto(updatedPhoto)

            DataState.Success(Unit)
        } catch (e: Exception) {
            DataState.Error<Unit>(ExceptionWrapper.Unknown)
        }
    }
}
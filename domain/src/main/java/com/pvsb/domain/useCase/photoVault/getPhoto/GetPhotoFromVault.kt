package com.pvsb.domain.useCase.photoVault.getPhoto

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Photo
import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.util.Logger

class GetPhotoFromVault(
    private val repository: PhotoVaultRepository,
    private val logger: Logger
) : GetPhotoFromVaultUseCase {
    override suspend fun invoke(photoId: Long): DataState<Photo> {
        return try {

            val photo = repository.getPhotoById(photoId) ?: throw NullPointerException(
                "No photo for id $photoId on vault"
            )

            DataState.Success(photo)
        } catch (e: Exception) {
            logger.e(e)
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}

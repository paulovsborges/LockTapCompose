package com.pvsb.domain.useCase.photoVault.addPhoto

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Photo
import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.util.Logger

class AddPhotoToVault(
    private val repository: PhotoVaultRepository,
    private val logger: Logger
) : AddPhotoToVaultUseCase {

    override suspend fun invoke(imageFilePath: String): DataState<Unit> {
        return try {

            val allPhotos = repository.getAllPhotos()
            val photoId = (allPhotos.size + 1).toLong()

            val photo = Photo(
                photoId,
                imageFilePath,
                false,
            )

            repository.addOrReplacePhoto(photo)

            DataState.Success(Unit)
        } catch (e: Exception) {
            logger.e(e)
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}

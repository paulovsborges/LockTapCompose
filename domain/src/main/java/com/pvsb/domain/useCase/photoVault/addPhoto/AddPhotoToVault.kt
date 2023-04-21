package com.pvsb.domain.useCase.photoVault.addPhoto

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Photo
import com.pvsb.domain.repository.PhotoVaultRepository

class AddPhotoToVault(
    private val repository: PhotoVaultRepository
) : AddPhotoToVaultUseCase {

    override suspend fun invoke(input: AddPhotoToVaultUseCase.Input): DataState<Unit> {
        return try {

            val allPhotos = repository.getAllPhotos()
            val photoId = (allPhotos.size + 1).toLong()

            val photo = Photo(
                photoId,
                input.imageFilePath,
                input.isFavorite
            )

            repository.addOrReplacePhoto(photo)

            DataState.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}
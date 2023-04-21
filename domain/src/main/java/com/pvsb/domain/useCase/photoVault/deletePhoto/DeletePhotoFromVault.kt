package com.pvsb.domain.useCase.photoVault.deletePhoto

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.repository.PhotoVaultRepository
import domain.useCase.photoVault.deletePhoto.DeletePhotoFromVaultUseCase

class DeletePhotoFromVault(
    private val repository: PhotoVaultRepository
) : DeletePhotoFromVaultUseCase {

    override suspend fun invoke(photoId: Long): DataState<Unit> {
        return try {

            repository.deletePhoto(photoId)

            DataState.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}
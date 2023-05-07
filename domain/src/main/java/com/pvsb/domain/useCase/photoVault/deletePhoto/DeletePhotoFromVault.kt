package com.pvsb.domain.useCase.photoVault.deletePhoto

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.util.Logger
import domain.useCase.photoVault.deletePhoto.DeletePhotoFromVaultUseCase

class DeletePhotoFromVault(
    private val repository: PhotoVaultRepository,
    private val logger: Logger
) : DeletePhotoFromVaultUseCase {

    override suspend fun invoke(photoId: Long): DataState<Unit> {
        return try {

            repository.deletePhoto(photoId)

            DataState.Success(Unit)
        } catch (e: Exception) {
            logger.e(e)
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}

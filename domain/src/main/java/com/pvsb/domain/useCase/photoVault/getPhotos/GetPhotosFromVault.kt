package com.pvsb.domain.useCase.photoVault.getPhotos

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Photo
import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.util.Logger

class GetPhotosFromVault(
    private val repository: PhotoVaultRepository,
    private val logger: Logger
) : GetPhotosFromVaultUseCase {

    override suspend fun invoke(): DataState<List<Photo>> {
        return try {
            DataState.Success(repository.getAllPhotos())
        } catch (e: Exception) {
            logger.e(e)
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}

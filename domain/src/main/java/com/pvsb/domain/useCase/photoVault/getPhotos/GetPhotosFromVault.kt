package com.pvsb.domain.useCase.photoVault.getPhotos

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Photo
import com.pvsb.domain.repository.PhotoVaultRepository

class GetPhotosFromVault(
    private val repository: PhotoVaultRepository
) : GetPhotosFromVaultUseCase {

    override suspend fun invoke(): DataState<List<Photo>> {
        return try {
            DataState.Success(repository.getAllPhotos())
        } catch (e: Exception) {
            e.printStackTrace()
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}

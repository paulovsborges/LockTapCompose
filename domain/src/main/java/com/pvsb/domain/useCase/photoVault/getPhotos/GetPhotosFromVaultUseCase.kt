package com.pvsb.domain.useCase.photoVault.getPhotos

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Photo

interface GetPhotosFromVaultUseCase {
    suspend operator fun invoke(): DataState<List<Photo>>
}
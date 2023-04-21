package com.pvsb.domain.useCase.photoVault.getPhoto

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Photo

interface GetPhotoFromVaultUseCase {
    suspend operator fun invoke(photoId: Long) : DataState<Photo>
}
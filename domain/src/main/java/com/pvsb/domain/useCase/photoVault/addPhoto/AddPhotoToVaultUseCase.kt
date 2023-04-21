package com.pvsb.domain.useCase.photoVault.addPhoto

import com.pvsb.domain.entity.DataState

interface AddPhotoToVaultUseCase {

    suspend operator fun invoke(imageFilePath: String): DataState<Unit>
}

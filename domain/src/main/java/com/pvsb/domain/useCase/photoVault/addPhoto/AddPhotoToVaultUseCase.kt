package com.pvsb.domain.useCase.photoVault.addPhoto

import com.pvsb.domain.entity.DataState

interface AddPhotoToVaultUseCase {

    suspend operator fun invoke(input: Input): DataState<Unit>

    data class Input(
        val imageFilePath: String,
        val isFavorite: Boolean
    )
}

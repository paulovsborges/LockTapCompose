package domain.useCase.photoVault.deletePhoto

import com.pvsb.domain.entity.DataState

interface DeletePhotoFromVaultUseCase {
    suspend operator fun invoke(photoId: Long): DataState<Unit>
}

package com.pvsb.locktapcompose.di.domain.useCase.photoVault

import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.useCase.photoVault.deletePhoto.DeletePhotoFromVault
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import domain.useCase.photoVault.deletePhoto.DeletePhotoFromVaultUseCase

@Module
@InstallIn(ViewModelComponent::class)
object DeletePhotoFromVaultUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        repository: PhotoVaultRepository
    ): DeletePhotoFromVaultUseCase {
        return DeletePhotoFromVault(repository)
    }
}

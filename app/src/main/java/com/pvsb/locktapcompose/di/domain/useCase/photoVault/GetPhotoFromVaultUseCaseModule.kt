package com.pvsb.locktapcompose.di.domain.useCase.photoVault

import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.useCase.photoVault.deletePhoto.DeletePhotoFromVault
import com.pvsb.domain.useCase.photoVault.getPhoto.GetPhotoFromVault
import com.pvsb.domain.useCase.photoVault.getPhoto.GetPhotoFromVaultUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import domain.useCase.photoVault.deletePhoto.DeletePhotoFromVaultUseCase

@Module
@InstallIn(ViewModelComponent::class)
object GetPhotoFromVaultUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        repository: PhotoVaultRepository
    ): GetPhotoFromVaultUseCase {
        return GetPhotoFromVault(repository)
    }
}
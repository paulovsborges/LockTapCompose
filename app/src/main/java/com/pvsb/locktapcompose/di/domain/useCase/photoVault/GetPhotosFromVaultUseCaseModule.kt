package com.pvsb.locktapcompose.di.domain.useCase.photoVault

import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.useCase.photoVault.getPhotos.GetPhotosFromVault
import com.pvsb.domain.useCase.photoVault.getPhotos.GetPhotosFromVaultUseCase
import com.pvsb.domain.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object GetPhotosFromVaultUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        repository: PhotoVaultRepository,
        logger: Logger
    ): GetPhotosFromVaultUseCase {
        return GetPhotosFromVault(repository, logger)
    }
}

package com.pvsb.locktapcompose.di.domain.useCase.photoVault

import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.useCase.photoVault.addPhoto.AddPhotoToVault
import com.pvsb.domain.useCase.photoVault.addPhoto.AddPhotoToVaultUseCase
import com.pvsb.domain.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AddPhotoToVaultUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        repository: PhotoVaultRepository,
        logger: Logger
    ): AddPhotoToVaultUseCase {
        return AddPhotoToVault(repository, logger)
    }
}

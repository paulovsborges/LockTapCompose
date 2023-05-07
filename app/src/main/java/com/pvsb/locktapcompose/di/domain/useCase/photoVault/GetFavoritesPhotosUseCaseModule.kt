package com.pvsb.locktapcompose.di.domain.useCase.photoVault

import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.useCase.photoVault.getFavorites.GetFavoritesPhotos
import com.pvsb.domain.useCase.photoVault.getFavorites.GetFavoritesPhotosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object GetFavoritesPhotosUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        photoVaultRepository: PhotoVaultRepository
    ): GetFavoritesPhotosUseCase {
        return GetFavoritesPhotos(photoVaultRepository)
    }
}

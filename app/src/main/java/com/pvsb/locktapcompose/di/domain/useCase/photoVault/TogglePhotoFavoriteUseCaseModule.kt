package com.pvsb.locktapcompose.di.domain.useCase.photoVault

import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.useCase.photoVault.togglePhotoFavorite.TogglePhotoFavorite
import com.pvsb.domain.useCase.photoVault.togglePhotoFavorite.TogglePhotoFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TogglePhotoFavoriteUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        repository: PhotoVaultRepository
    ): TogglePhotoFavoriteUseCase {
        return TogglePhotoFavorite(repository)
    }
}

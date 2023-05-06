package com.pvsb.locktapcompose.di.domain.useCase.passwords

import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.domain.useCase.photoVault.getFavorites.GetFavoritesPasswords
import com.pvsb.domain.useCase.password.getFavorites.GetFavoritesPasswordsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object GetFavoritesPasswordsUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        passwordsRepository: PasswordsRepository
    ): GetFavoritesPasswordsUseCase {
        return GetFavoritesPasswords(passwordsRepository)
    }
}
package com.pvsb.locktapcompose.di.domain.passwords

import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.domain.useCase.password.togglePasswordFavorite.TogglePasswordFavorite
import com.pvsb.domain.useCase.password.togglePasswordFavorite.TogglePasswordFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TogglePasswordFavoriteUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        passwordsRepository: PasswordsRepository
    ): TogglePasswordFavoriteUseCase {
        return TogglePasswordFavorite(passwordsRepository)
    }
}
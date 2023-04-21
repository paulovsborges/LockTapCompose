package com.pvsb.locktapcompose.di.domain.useCase.passwords

import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.domain.useCase.password.getPassword.GetPassword
import com.pvsb.domain.useCase.password.getPassword.GetPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object GetPasswordUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        passwordsRepository: PasswordsRepository
    ): GetPasswordUseCase {
        return GetPassword(passwordsRepository)
    }
}
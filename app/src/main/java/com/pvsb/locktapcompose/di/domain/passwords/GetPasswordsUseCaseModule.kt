package com.pvsb.locktapcompose.di.domain.passwords

import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.domain.useCase.password.getPassword.GetPassword
import com.pvsb.domain.useCase.password.getPassword.GetPasswordUseCase
import com.pvsb.domain.useCase.password.getPasswords.GetPasswords
import com.pvsb.domain.useCase.password.getPasswords.GetPasswordsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object GetPasswordsUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        passwordsRepository: PasswordsRepository
    ): GetPasswordsUseCase {
        return GetPasswords(passwordsRepository)
    }
}
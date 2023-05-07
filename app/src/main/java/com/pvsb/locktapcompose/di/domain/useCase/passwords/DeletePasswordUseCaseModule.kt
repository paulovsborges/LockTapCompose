package com.pvsb.locktapcompose.di.domain.useCase.passwords

import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.domain.useCase.password.deletePassword.DeletePassword
import com.pvsb.domain.useCase.password.deletePassword.DeletePasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DeletePasswordUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        passwordsRepository: PasswordsRepository
    ): DeletePasswordUseCase {
        return DeletePassword(passwordsRepository)
    }
}
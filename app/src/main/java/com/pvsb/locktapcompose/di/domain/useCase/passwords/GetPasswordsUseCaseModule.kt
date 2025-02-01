package com.pvsb.locktapcompose.di.domain.useCase.passwords

import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.domain.useCase.password.getPasswords.GetPasswords
import com.pvsb.domain.useCase.password.getPasswords.GetPasswordsUseCase
import com.pvsb.domain.util.Logger
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
        passwordsRepository: PasswordsRepository,
        logger: Logger
    ): GetPasswordsUseCase {
        return GetPasswords(passwordsRepository, logger)
    }
}

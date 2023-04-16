package com.pvsb.locktapcompose.di.domain.user

import com.pvsb.domain.repository.UserRepository
import com.pvsb.domain.useCase.login.Login
import com.pvsb.domain.useCase.login.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object LoginUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        userRepository: UserRepository
    ): LoginUseCase {
        return Login(userRepository)
    }
}

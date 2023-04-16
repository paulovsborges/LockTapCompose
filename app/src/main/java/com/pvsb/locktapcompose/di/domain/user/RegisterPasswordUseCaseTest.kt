package com.pvsb.locktapcompose.di.domain.user

import com.pvsb.domain.repository.UserRepository
import com.pvsb.domain.useCase.password.registerPassword.RegisterPassword
import com.pvsb.domain.useCase.password.registerPassword.RegisterPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RegisterPasswordUseCaseTest {

    @Provides
    @ViewModelScoped
    fun provides(
        userRepository: UserRepository
    ): RegisterPasswordUseCase {
        return RegisterPassword(userRepository)
    }
}

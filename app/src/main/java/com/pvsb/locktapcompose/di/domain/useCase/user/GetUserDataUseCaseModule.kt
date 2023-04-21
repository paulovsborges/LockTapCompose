package com.pvsb.locktapcompose.di.domain.useCase.user

import com.pvsb.domain.repository.UserRepository
import com.pvsb.domain.useCase.getUserData.GetUserData
import com.pvsb.domain.useCase.getUserData.GetUserDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object GetUserDataUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        userRepository: UserRepository
    ): GetUserDataUseCase {
        return GetUserData(userRepository)
    }
}

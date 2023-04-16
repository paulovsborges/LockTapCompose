package com.pvsb.locktapcompose.di.domain.user

import com.pvsb.domain.repository.UserRepository
import com.pvsb.domain.useCase.skipOnBoarding.SkipOnBoarding
import com.pvsb.domain.useCase.skipOnBoarding.SkipOnBoardingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SkipOnBoardingUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        userRepository: UserRepository
    ): SkipOnBoardingUseCase {
        return SkipOnBoarding(userRepository)
    }
}

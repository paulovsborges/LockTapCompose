package com.pvsb.locktapcompose.di.datasource.repository

import com.pvsb.datasource.local.dataStore.LocalDataManager
import com.pvsb.datasource.repository.user.UserManagerRepository
import com.pvsb.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserRepositoryModule {

    @Provides
    @Singleton
    fun provides(
        localDataManager: LocalDataManager
    ): UserRepository{
        return UserManagerRepository(localDataManager)
    }
}
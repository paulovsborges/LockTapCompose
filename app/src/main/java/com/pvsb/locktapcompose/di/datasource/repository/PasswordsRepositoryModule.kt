package com.pvsb.locktapcompose.di.datasource.repository

import com.pvsb.datasource.repository.passwords.PasswordsSqlDelightRepository
import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.locktapcompose.LockTapDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PasswordsRepositoryModule {

    @Provides
    @Singleton
    fun provides(
        db: LockTapDataBase
    ): PasswordsRepository {
        return PasswordsSqlDelightRepository(db)
    }
}

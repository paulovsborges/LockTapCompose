package com.pvsb.locktapcompose.di.datasource.repository

import com.pvsb.datasource.repository.photoVault.PhotoVaultSqlDelightRepository
import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.locktapcompose.LockTapDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoVaultRepositoryModule {

    @Provides
    @Singleton
    fun provides(
        db: LockTapDataBase
    ): PhotoVaultRepository {
        return PhotoVaultSqlDelightRepository(db)
    }
}

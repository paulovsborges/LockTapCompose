package com.pvsb.locktapcompose.di.datasource.repository

import app.cash.sqldelight.db.SqlDriver
import com.pvsb.datasource.repository.ContactsSqlDelightRepository
import com.pvsb.domain.repository.ContactsRepository
import com.pvsb.locktapcompose.LockTapDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContactsRepositoryModule {

    @Provides
    @Singleton
    fun provides(
        driver: SqlDriver
    ): ContactsRepository {
        return ContactsSqlDelightRepository(
            LockTapDataBase(
                driver
            )
        )
    }
}

package com.pvsb.locktapcompose.di.datasource.sqlDelight

import app.cash.sqldelight.db.SqlDriver
import com.pvsb.locktapcompose.LockTapDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LockTapDataBaseModule {

    @Provides
    @Singleton
    fun provides(
        driver: SqlDriver
    ): LockTapDataBase {
        return LockTapDataBase(
            driver
        )
    }
}

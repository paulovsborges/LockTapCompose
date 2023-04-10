package com.pvsb.locktapcompose.di.datasource.sqlDelight

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.pvsb.locktapcompose.LockTapDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AndroidSqlDriverModule {

    @Provides
    @Singleton
    fun provides(@ApplicationContext context: Context): SqlDriver {
        return AndroidSqliteDriver(
            schema = LockTapDataBase.Schema,
            context = context,
            name = "locktap.db"
        )
    }
}

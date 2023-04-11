package com.pvsb.locktapcompose.di.datasource.local.dataStore

import android.content.Context
import com.pvsb.datasource.local.dataStore.LocalDataManager
import com.pvsb.datasource.local.dataStore.LocalDataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataManagerModule {

    @Provides
    @Singleton
    fun provides(
        @ApplicationContext context: Context
    ): LocalDataManager {
        return LocalDataStoreManager(context)
    }
}

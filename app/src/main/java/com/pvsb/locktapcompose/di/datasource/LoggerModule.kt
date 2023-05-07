package com.pvsb.locktapcompose.di.datasource

import com.pvsb.datasource.LoggerImpl
import com.pvsb.domain.util.Logger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LoggerModule {
    @Binds
    fun provides(impl: LoggerImpl): Logger
}
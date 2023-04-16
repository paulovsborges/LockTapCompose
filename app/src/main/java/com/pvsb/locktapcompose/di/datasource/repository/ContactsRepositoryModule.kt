package com.pvsb.locktapcompose.di.datasource.repository

import com.pvsb.datasource.repository.contacts.ContactsSqlDelightRepository
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
        db: LockTapDataBase
    ): ContactsRepository {
        return ContactsSqlDelightRepository(db)
    }
}

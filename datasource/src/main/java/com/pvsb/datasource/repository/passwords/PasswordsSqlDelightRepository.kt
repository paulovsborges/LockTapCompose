package com.pvsb.datasource.repository.passwords

import com.pvsb.domain.entity.Password
import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.locktapcompose.LockTapDataBase

class PasswordsSqlDelightRepository(
    db: LockTapDataBase
): PasswordsRepository {

    private val queries = db.passwordEntityQueries

    override suspend fun add(password: Password) {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<Password> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(passwordId: Int) {
        TODO("Not yet implemented")
    }
}
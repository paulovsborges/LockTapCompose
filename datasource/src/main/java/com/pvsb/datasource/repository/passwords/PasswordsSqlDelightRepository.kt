package com.pvsb.datasource.repository.passwords

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.pvsb.datasource.mapper.password.PasswordMapper.toEntity
import com.pvsb.datasource.mapper.password.PasswordMapper.toModel
import com.pvsb.domain.entity.Password
import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.locktapcompose.LockTapDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PasswordsSqlDelightRepository(
    db: LockTapDataBase
) : PasswordsRepository {

    private val queries = db.passwordEntityQueries

    override suspend fun add(password: Password) {
        queries.insertOrReplace(password.toEntity())
    }

    override suspend fun getAll(): List<Password> {
        return queries.getAll().executeAsList().map { it.toModel() }
    }

    override suspend fun getAllAsFlow(): Flow<List<Password>> {
        return queries.getAll().asFlow().mapToList(Dispatchers.Main).map {
            it.map { entity -> entity.toModel() }
        }
    }

    override suspend fun delete(passwordId: Int) {
        queries.delete(passwordId.toLong())
    }

    override suspend fun getPassword(passwordId: Int): Password? {
        return queries.getPasswordById(passwordId.toLong()).executeAsOneOrNull()?.toModel()
    }
}

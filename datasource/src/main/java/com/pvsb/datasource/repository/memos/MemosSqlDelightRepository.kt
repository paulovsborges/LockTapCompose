package com.pvsb.datasource.repository.memos

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.pvsb.datasource.mapper.memos.MemoMapper.toEntity
import com.pvsb.datasource.mapper.memos.MemoMapper.toModel
import com.pvsb.domain.entity.Memo
import com.pvsb.domain.repository.MemoRepository
import com.pvsb.locktapcompose.LockTapDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MemosSqlDelightRepository(
    db: LockTapDataBase
) : MemoRepository {

    private val queries = db.memoEntityQueries

    override suspend fun add(memo: Memo) {
        queries.insertOrReplace(memo.toEntity())
    }

    override suspend fun delete(id: String) {
        queries.delete(id.toLong())
    }

    override suspend fun getAll(): List<Memo> {
        return queries.getAll().executeAsList().map { it.toModel() }
    }

    override suspend fun getAllAsFlow(): Flow<List<Memo>> {
        return queries.getAll().asFlow().mapToList(Dispatchers.Main).map {
            it.map { entity -> entity.toModel() }
        }
    }

    override suspend fun get(id: String): Memo? {
        return queries.getMemoById(id.toLong()).executeAsOneOrNull()?.toModel()
    }
}
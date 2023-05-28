package com.pvsb.datasource.repository.memos

import com.pvsb.domain.entity.Memo
import com.pvsb.domain.repository.MemoRepository
import com.pvsb.locktapcompose.LockTapDataBase
import kotlinx.coroutines.flow.Flow

class MemosSqlDelightRepository(
    db: LockTapDataBase
): MemoRepository {

    private val queries = db.memoEntityQueries

    override suspend fun add(memo: Memo) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<Memo> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllAsFlow(): Flow<List<Memo>> {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: String): Memo? {
        TODO("Not yet implemented")
    }
}
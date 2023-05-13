package com.pvsb.domain.repository

import com.pvsb.domain.entity.Memo
import kotlinx.coroutines.flow.Flow

interface MemoRepository {
    suspend fun add(memo: Memo)
    suspend fun delete(id: String)
    suspend fun getAll(): List<Memo>
    suspend fun getAllAsFlow(): Flow<List<Memo>>
    suspend fun get(id: String): Memo
}
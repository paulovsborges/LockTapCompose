package com.pvsb.datasource.local.dataStore

import kotlinx.coroutines.flow.Flow

interface LocalDataManager {
    suspend fun read(keyName: String): Flow<String?>
    suspend fun write(keyName: String, value: String)
    suspend fun delete(keyName: String)
    suspend fun removeKey(key: String)
    suspend fun clear()
}

package com.pvsb.datasource.local.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class LocalDataStoreManager(
    private val context: Context
) : LocalDataManager {

    private companion object {
        @JvmStatic
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "locktapp_storage")
    }

    override suspend fun read(keyName: String): Flow<String?> {
        return context.dataStore.data
            .catch {
                throw it
            }
            .map {
                val keyPref = stringPreferencesKey(keyName)
                it[keyPref]
            }
    }

    override suspend fun write(keyName: String, value: String) {
        context.dataStore.edit {
            val keyPref = stringPreferencesKey(keyName)
            it[keyPref] = value
        }
    }

    override suspend fun delete(keyName: String) {
        context.dataStore.edit {
            val keyPref = stringPreferencesKey(keyName)
            it.remove(keyPref)
        }
    }

    override suspend fun removeKey(key: String) {
        context.dataStore.edit { it.remove(stringPreferencesKey(key)) }
    }

    override suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}

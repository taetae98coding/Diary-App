package io.github.taetae98coding.diary.core.preferences.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import io.github.taetae98coding.diary.core.datastore.di.MemoFetchDataStore
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
public class MemoFetchPreferencesDataSource internal constructor(
    @param:MemoFetchDataStore
    private val dataStore: DataStore<Preferences>,
) {
    public suspend fun updateLastUpdatedAt(accountId: Uuid, lastUpdatedAt: Long) {
        dataStore.edit { it[lastUpdatedAtKey(accountId)] = lastUpdatedAt }
    }

    public fun getLastUpdatedAt(accountId: Uuid): Flow<Long?> {
        return dataStore.data.mapLatest { it[lastUpdatedAtKey(accountId)] }
    }

    private fun lastUpdatedAtKey(accountId: Uuid): Preferences.Key<Long> {
        return longPreferencesKey("lastUpdatedAt_$accountId")
    }
}

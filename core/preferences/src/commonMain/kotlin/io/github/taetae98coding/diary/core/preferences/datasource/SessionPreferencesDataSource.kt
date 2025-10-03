package io.github.taetae98coding.diary.core.preferences.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.github.taetae98coding.diary.core.datastore.di.SessionDataStore
import io.github.taetae98coding.diary.core.preferences.entity.SessionPreferencesEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
public class SessionPreferencesDataSource internal constructor(
    @param:SessionDataStore
    private val dataStore: DataStore<Preferences>,
) {
    public suspend fun update(session: SessionPreferencesEntity) {
        dataStore.edit {
            it[tokenKey()] = session.token
            it[idKey()] = session.id.toString()
            it[emailKey()] = session.email

            if (session.profileImage == null) {
                it.remove(profileImageKey())
            } else {
                it[profileImageKey()] = session.profileImage
            }
        }
    }

    public suspend fun delete() {
        dataStore.edit {
            it.remove(tokenKey())
            it.remove(idKey())
            it.remove(emailKey())
            it.remove(profileImageKey())
        }
    }

    public fun get(): Flow<SessionPreferencesEntity?> {
        return dataStore.data.mapLatest { preferences ->
            SessionPreferencesEntity(
                token = preferences[tokenKey()] ?: return@mapLatest null,
                id = preferences[idKey()]?.let(Uuid::parse) ?: return@mapLatest null,
                email = preferences[emailKey()] ?: return@mapLatest null,
                profileImage = preferences[profileImageKey()],
            )
        }
    }

    private fun tokenKey(): Preferences.Key<String> {
        return stringPreferencesKey("token")
    }

    private fun idKey(): Preferences.Key<String> {
        return stringPreferencesKey("id")
    }

    private fun emailKey(): Preferences.Key<String> {
        return stringPreferencesKey("email")
    }

    private fun profileImageKey(): Preferences.Key<String> {
        return stringPreferencesKey("profileImage")
    }
}

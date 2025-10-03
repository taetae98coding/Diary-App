package io.github.taetae98coding.diary.data.credentials.repository

import io.github.taetae98coding.diary.core.mapper.toPreferences
import io.github.taetae98coding.diary.core.preferences.datasource.SessionPreferencesDataSource
import io.github.taetae98coding.diary.core.service.datasource.SessionRemoteDataSource
import io.github.taetae98coding.diary.domain.credentials.repository.CredentialsRepository
import org.koin.core.annotation.Factory

@Factory
internal class CredentialsRepositoryImpl(
    private val sessionPreferencesDataSource: SessionPreferencesDataSource,
    private val sessionRemoteDataSource: SessionRemoteDataSource,
) : CredentialsRepository {
    override suspend fun updateGoogleSession(idToken: String) {
        val session = sessionRemoteDataSource.getGoogleSession(idToken)
            .requireSuccess()
            .requireBody()

        sessionPreferencesDataSource.update(session.toPreferences())
    }

    override suspend fun delete() {
        sessionPreferencesDataSource.delete()
    }
}

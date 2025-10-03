package io.github.taetae98coding.diary.data.account.repository

import io.github.taetae98coding.diary.core.entity.account.Session
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.core.preferences.datasource.SessionPreferencesDataSource
import io.github.taetae98coding.diary.domain.account.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Single

@Single
internal class SessionRepositoryImpl(
    private val sessionPreferencesDataSource: SessionPreferencesDataSource,
) : SessionRepository {

    override fun get(): Flow<Session?> {
        return sessionPreferencesDataSource.get()
            .mapLatest { it?.toEntity() }
    }
}

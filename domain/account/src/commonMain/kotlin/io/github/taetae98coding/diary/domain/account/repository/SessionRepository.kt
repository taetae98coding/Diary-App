package io.github.taetae98coding.diary.domain.account.repository

import io.github.taetae98coding.diary.core.entity.account.Session
import kotlinx.coroutines.flow.Flow

public interface SessionRepository {
    public fun get(): Flow<Session?>
}

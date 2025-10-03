package io.github.taetae98coding.diary.domain.location.repository

import io.github.taetae98coding.diary.library.kotlinx.location.Location
import kotlinx.coroutines.flow.Flow

public interface LocationRepository {
    public fun get(): Flow<Location>
}

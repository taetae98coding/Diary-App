package io.github.taetae98coding.diary.core.location

import io.github.taetae98coding.diary.core.entity.location.Location

public interface LocationManager {
    public suspend fun getLastLocation(): Location?
}

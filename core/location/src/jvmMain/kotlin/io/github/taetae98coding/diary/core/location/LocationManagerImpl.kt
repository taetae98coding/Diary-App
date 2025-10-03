package io.github.taetae98coding.diary.core.location

import io.github.taetae98coding.diary.core.entity.location.Location
import org.koin.core.annotation.Factory

@Factory
internal class LocationManagerImpl : LocationManager {
    override suspend fun getLastLocation(): Location? {
        return null
    }
}

package io.github.taetae98coding.diary.core.location

import io.github.taetae98coding.diary.core.entity.location.Location
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import org.koin.core.annotation.Factory
import platform.CoreLocation.CLLocationManager

@Factory
@OptIn(ExperimentalForeignApi::class)
internal class LocationManagerImpl : LocationManager {
    override suspend fun getLastLocation(): Location? {
        val manager = CLLocationManager()
            .apply { delegate = LocationManagerDelegate() }
            .also { it.requestWhenInUseAuthorization() }

        val location = manager.location() ?: return null

        return location.coordinate().useContents { Location(latitude, longitude) }
    }
}

package io.github.taetae98coding.diary.core.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import io.github.taetae98coding.diary.core.entity.location.Location
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Factory

@Factory
internal class LocationManagerImpl(
    private val context: Context,
) : LocationManager {
    override suspend fun getLastLocation(): Location? {
        val permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )

        if (permissions.any { ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED }) {
            return null
        }

        val client = LocationServices.getFusedLocationProviderClient(context)
        val location = client.lastLocation.await() ?: return null

        return Location(
            latitude = location.latitude,
            longitude = location.longitude,
        )
    }
}

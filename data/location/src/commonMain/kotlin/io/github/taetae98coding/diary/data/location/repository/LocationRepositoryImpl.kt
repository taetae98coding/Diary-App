package io.github.taetae98coding.diary.data.location.repository

import io.github.taetae98coding.diary.core.entity.location.Location
import io.github.taetae98coding.diary.core.ip.service.datasource.IpRemoteDataSource
import io.github.taetae98coding.diary.core.location.LocationManager
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.location.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
internal class LocationRepositoryImpl(
    private val ipRemoteDataSource: IpRemoteDataSource,
    private val locationManager: LocationManager,
) : LocationRepository {
    override fun get(): Flow<Location> {
        return flow {
            val deviceLocation = locationManager.getLastLocation()

            if (deviceLocation == null) {
                emit(ipRemoteDataSource.get().toEntity())
            } else {
                emit(deviceLocation)
            }
        }
    }
}

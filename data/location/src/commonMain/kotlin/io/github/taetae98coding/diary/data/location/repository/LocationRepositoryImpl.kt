package io.github.taetae98coding.diary.data.location.repository

import io.github.taetae98coding.diary.core.ip.service.datasource.IpRemoteDataSource
import io.github.taetae98coding.diary.domain.location.repository.LocationRepository
import io.github.taetae98coding.diary.library.kotlinx.location.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
internal class LocationRepositoryImpl(
    private val ipRemoteDataSource: IpRemoteDataSource,
) : LocationRepository {
    override fun get(): Flow<Location> {
        return flow {
            val remote = ipRemoteDataSource.get()
            emit(Location(remote.latitude, remote.longitude))
        }
    }
}

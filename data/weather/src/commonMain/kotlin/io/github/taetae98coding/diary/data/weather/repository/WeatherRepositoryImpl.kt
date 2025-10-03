package io.github.taetae98coding.diary.data.weather.repository

import io.github.taetae98coding.diary.core.entity.location.Location
import io.github.taetae98coding.diary.core.entity.weather.Weather
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.core.weather.service.datasource.WeatherRemoteDataSource
import io.github.taetae98coding.diary.core.weather.service.entity.WeatherRemoteEntity
import io.github.taetae98coding.diary.domain.weather.repository.WeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import org.koin.core.annotation.Factory

@Factory
internal class WeatherRepositoryImpl(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
) : WeatherRepository {
    override fun get(location: Location): Flow<List<Weather>> {
        return channelFlow {
            coroutineScope {
                listOf(async { runCatching { listOf(weatherRemoteDataSource.getWeather(location.latitude, location.longitude)) } }, async { runCatching { weatherRemoteDataSource.getForecast(location.latitude, location.longitude).weatherList } })
                    .awaitAll()
                    .mapNotNull { it.getOrNull() }
                    .flatten()
                    .map(WeatherRemoteEntity::toEntity)
                    .distinctBy { it.instant }
                    .sortedBy { it.instant }
            }.also {
                send(it)
            }
        }
    }
}

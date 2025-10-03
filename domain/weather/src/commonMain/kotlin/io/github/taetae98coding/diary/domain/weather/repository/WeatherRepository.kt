package io.github.taetae98coding.diary.domain.weather.repository

import io.github.taetae98coding.diary.core.entity.location.Location
import io.github.taetae98coding.diary.core.entity.weather.Weather
import kotlinx.coroutines.flow.Flow

public interface WeatherRepository {
    public fun get(location: Location): Flow<List<Weather>>
}

package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.entity.weather.Weather
import io.github.taetae98coding.diary.core.weather.service.entity.WeatherRemoteEntity
import io.github.taetae98coding.diary.core.weather.service.entity.WeatherTypeRemoteEntity

public fun WeatherRemoteEntity.toEntity(): Weather {
    return Weather(
        type = type.map(WeatherTypeRemoteEntity::toEntity),
        temperature = main.temperature,
        instant = instant,
    )
}

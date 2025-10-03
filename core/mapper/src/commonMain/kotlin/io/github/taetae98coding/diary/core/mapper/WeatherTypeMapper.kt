package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.entity.weather.WeatherType
import io.github.taetae98coding.diary.core.weather.service.entity.WeatherTypeRemoteEntity

public fun WeatherTypeRemoteEntity.toEntity(): WeatherType {
    return WeatherType(
        icon = icon,
        description = description,
    )
}

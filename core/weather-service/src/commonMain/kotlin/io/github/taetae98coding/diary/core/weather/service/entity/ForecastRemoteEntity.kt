package io.github.taetae98coding.diary.core.weather.service.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class ForecastRemoteEntity(
    @SerialName("list")
    val weatherList: List<WeatherRemoteEntity>,
)

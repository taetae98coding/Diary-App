package io.github.taetae98coding.diary.core.weather.service.entity

import io.github.taetae98coding.diary.core.weather.service.serialization.WeatherInstantSerializer
import kotlin.time.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class WeatherRemoteEntity(
    @SerialName("weather")
    val type: List<WeatherTypeRemoteEntity>,
    @SerialName("main")
    val main: WeatherMainRemoteEntity,
    @Serializable(WeatherInstantSerializer::class)
    @SerialName("dt")
    val instant: Instant,
)

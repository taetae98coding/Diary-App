package io.github.taetae98coding.diary.core.entity.weather

import kotlin.time.Instant

public data class Weather(
    val type: List<WeatherType>,
    val temperature: Double,
    val instant: Instant,
)

package io.github.taetae98coding.diary.core.weather.service.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class WeatherTypeRemoteEntity(
    @SerialName("id")
    val id: Int,
    @SerialName("description")
    val description: String,
    @SerialName("icon")
    val iconId: String,
) {
    val icon: String
        get() = "https://openweathermap.org/img/wn/$iconId.png"
}

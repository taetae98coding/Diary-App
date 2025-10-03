package io.github.taetae98coding.diary.core.ip.service.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class LocationRemoteEntity(
    @SerialName("lat")
    val latitude: Double,
    @SerialName("lon")
    val longitude: Double,
)

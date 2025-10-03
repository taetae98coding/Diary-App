package io.githbu.taetae98coding.diary.core.holiday.service.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
internal data class OpenApiBodyEntity(
    @SerialName("items")
    val items: JsonElement,
    @SerialName("totalCount")
    val totalCount: Int,
)

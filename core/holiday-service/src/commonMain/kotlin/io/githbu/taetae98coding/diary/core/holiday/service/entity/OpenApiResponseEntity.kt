package io.githbu.taetae98coding.diary.core.holiday.service.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OpenApiResponseEntity(
    @SerialName("body")
    val body: OpenApiBodyEntity,
)

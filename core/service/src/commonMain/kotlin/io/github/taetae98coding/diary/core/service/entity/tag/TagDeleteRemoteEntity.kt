package io.github.taetae98coding.diary.core.service.entity.tag

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class TagDeleteRemoteEntity(
    @SerialName("isDeleted")
    val isDeleted: Boolean,
)

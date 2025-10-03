package io.github.taetae98coding.diary.core.service.entity.tag

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class SelectMemoTagRemoteEntity(
    @SerialName("isSelected")
    val isSelected: Boolean,
    @SerialName("tag")
    val tag: TagRemoteEntity,
)

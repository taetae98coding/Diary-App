package io.github.taetae98coding.diary.core.service.entity.memo

import kotlin.uuid.Uuid
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class MemoPrimaryRemoteEntity(
    @SerialName("primaryTag")
    val primaryTag: Uuid?,
)

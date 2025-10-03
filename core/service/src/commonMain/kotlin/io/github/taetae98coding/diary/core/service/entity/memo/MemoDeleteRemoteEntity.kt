package io.github.taetae98coding.diary.core.service.entity.memo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class MemoDeleteRemoteEntity(
    @SerialName("isDeleted")
    val isDeleted: Boolean,
)

package io.github.taetae98coding.diary.core.service.entity.memo

import kotlin.uuid.Uuid
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class MemoTagRemoteEntity(
    @SerialName("memoId")
    val memoId: Uuid,
    @SerialName("tagId")
    val tagId: Uuid,
    @SerialName("isFinished")
    val isMemoTag: Boolean,
    @SerialName("updatedAt")
    val updatedAt: Long,
)

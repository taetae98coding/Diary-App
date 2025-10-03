package io.github.taetae98coding.diary.core.service.entity.memotag

import kotlin.uuid.Uuid
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class UpdateMemoTagRequestEntity(
    @SerialName("memoId")
    val memoId: Uuid,
    @SerialName("tagId")
    val tagId: Uuid,
    @SerialName("isMemoTag")
    val isMemoTag: Boolean,
)

package io.github.taetae98coding.diary.core.service.entity.memotag

import io.github.taetae98coding.diary.core.service.entity.memo.MemoRemoteEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class UpdateMemoTagResultEntity(
    @SerialName("memo")
    val memo: MemoRemoteEntity,
    @SerialName("memoTag")
    val memoTag: MemoTagRemoteEntity,
)

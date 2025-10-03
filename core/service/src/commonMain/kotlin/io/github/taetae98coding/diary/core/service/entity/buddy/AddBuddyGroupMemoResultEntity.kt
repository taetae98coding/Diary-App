package io.github.taetae98coding.diary.core.service.entity.buddy

import io.github.taetae98coding.diary.core.service.entity.memo.MemoRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.memotag.MemoTagRemoteEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class AddBuddyGroupMemoResultEntity(
    @SerialName("memo")
    val memo: MemoRemoteEntity,
    @SerialName("memoTags")
    val memoTags: List<MemoTagRemoteEntity>,
)

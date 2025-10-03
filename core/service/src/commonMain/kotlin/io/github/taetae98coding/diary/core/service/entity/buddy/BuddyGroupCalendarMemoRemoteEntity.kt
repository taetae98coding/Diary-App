package io.github.taetae98coding.diary.core.service.entity.buddy

import io.github.taetae98coding.diary.core.service.entity.memo.MemoRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.tag.TagRemoteEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class BuddyGroupCalendarMemoRemoteEntity(
    @SerialName("memo")
    val memo: List<MemoRemoteEntity>,
    @SerialName("tag")
    val tag: List<TagRemoteEntity>,
)

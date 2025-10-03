package io.github.taetae98coding.diary.core.service.entity.buddy

import io.github.taetae98coding.diary.core.service.entity.memo.MemoDetailRemoteEntity
import kotlin.uuid.Uuid
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class AddBuddyGroupMemoRequestEntity(
    @SerialName("detail")
    val detail: MemoDetailRemoteEntity,
    @SerialName("primaryTag")
    val primaryTag: Uuid?,
    @SerialName("memoTagIds")
    val memoTagIds: Set<Uuid>,
)

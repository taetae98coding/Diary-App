package io.github.taetae98coding.diary.core.service.entity.buddy

import io.github.taetae98coding.diary.core.service.entity.tag.TagDetailRemoteEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class AddBuddyGroupTagRemoteEntity(
    @SerialName("detail")
    val detail: TagDetailRemoteEntity,
)

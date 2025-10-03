package io.github.taetae98coding.diary.core.service.entity.buddy

import kotlin.uuid.Uuid
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class BuddyAddRemoteEntity(
    @SerialName("detail")
    val detail: BuddyGroupDetailRemoteEntity,
    @SerialName("buddyIds")
    val buddyIds: Set<Uuid>,
)

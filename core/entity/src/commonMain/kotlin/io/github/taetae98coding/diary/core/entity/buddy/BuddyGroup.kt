package io.github.taetae98coding.diary.core.entity.buddy

import kotlin.uuid.Uuid

public data class BuddyGroup(
    val id: Uuid,
    val detail: BuddyGroupDetail,
)

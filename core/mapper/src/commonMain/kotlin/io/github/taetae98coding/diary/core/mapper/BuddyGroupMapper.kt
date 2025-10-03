package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.database.entity.BuddyGroupLocalEntity
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroup
import io.github.taetae98coding.diary.core.service.entity.buddy.BuddyGroupRemoteEntity

public fun BuddyGroupLocalEntity.toEntity(): BuddyGroup {
    return BuddyGroup(
        id = id,
        detail = detail.toEntity(),
    )
}

public fun BuddyGroupRemoteEntity.toLocal(): BuddyGroupLocalEntity {
    return BuddyGroupLocalEntity(
        id = id,
        detail = detail.toLocal(),
    )
}

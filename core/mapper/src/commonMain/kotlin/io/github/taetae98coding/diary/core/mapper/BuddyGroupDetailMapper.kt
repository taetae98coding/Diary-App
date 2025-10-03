package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.database.entity.BuddyGroupDetailLocalEntity
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroupDetail
import io.github.taetae98coding.diary.core.service.entity.buddy.BuddyGroupDetailRemoteEntity

public fun BuddyGroupDetail.toLocal(): BuddyGroupDetailLocalEntity {
    return BuddyGroupDetailLocalEntity(
        title = title,
        description = description,
    )
}

public fun BuddyGroupDetail.toRemote(): BuddyGroupDetailRemoteEntity {
    return BuddyGroupDetailRemoteEntity(
        title = title,
        description = description,
    )
}

public fun BuddyGroupDetailLocalEntity.toEntity(): BuddyGroupDetail {
    return BuddyGroupDetail(
        title = title,
        description = description,
    )
}

public fun BuddyGroupDetailRemoteEntity.toLocal(): BuddyGroupDetailLocalEntity {
    return BuddyGroupDetailLocalEntity(
        title = title,
        description = description,
    )
}

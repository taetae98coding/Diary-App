package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.entity.buddy.Buddy
import io.github.taetae98coding.diary.core.service.entity.buddy.BuddyRemoteEntity

public fun BuddyRemoteEntity.toEntity(): Buddy {
    return Buddy(
        id = id,
        email = email,
        profileImage = profileImage,
    )
}

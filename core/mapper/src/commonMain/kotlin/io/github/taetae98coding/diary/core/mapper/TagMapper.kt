package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.service.entity.tag.TagRemoteEntity

public fun Tag.toLocal(): TagLocalEntity {
    return TagLocalEntity(
        id = id,
        detail = detail.toLocal(),
        isFinished = isFinished,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        serverUpdatedAt = -1L,
    )
}

public fun TagLocalEntity.toEntity(): Tag {
    return Tag(
        id = id,
        detail = detail.toEntity(),
        isFinished = isFinished,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

public fun TagLocalEntity.toRemote(): TagRemoteEntity {
    return TagRemoteEntity(
        id = id,
        detail = detail.toRemote(),
        isFinished = isFinished,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

public fun TagRemoteEntity.toLocal(): TagLocalEntity {
    return TagLocalEntity(
        id = id,
        detail = detail.toLocal(),
        isFinished = isFinished,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        serverUpdatedAt = updatedAt,
    )
}

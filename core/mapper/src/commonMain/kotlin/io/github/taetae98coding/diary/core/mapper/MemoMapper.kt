package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.service.entity.memo.MemoRemoteEntity

public fun Memo.toLocal(): MemoLocalEntity {
    return MemoLocalEntity(
        id = id,
        detail = detail.toLocal(),
        primaryTag = primaryTag,
        isFinished = isFinished,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        serverUpdatedAt = -1L,
    )
}

public fun MemoLocalEntity.toEntity(): Memo {
    return Memo(
        id = id,
        detail = detail.toEntity(),
        primaryTag = primaryTag,
        isFinished = isFinished,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

public fun MemoLocalEntity.toRemote(): MemoRemoteEntity {
    return MemoRemoteEntity(
        id = id,
        detail = detail.toRemote(),
        primaryTag = primaryTag,
        isFinished = isFinished,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

public fun MemoRemoteEntity.toLocal(): MemoLocalEntity {
    return MemoLocalEntity(
        id = id,
        detail = detail.toLocal(),
        primaryTag = primaryTag,
        isFinished = isFinished,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        serverUpdatedAt = updatedAt,
    )
}

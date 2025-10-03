package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.database.entity.MemoTagLocalEntity
import io.github.taetae98coding.diary.core.service.entity.memotag.MemoTagRemoteEntity

public fun MemoTagLocalEntity.toRemote(): MemoTagRemoteEntity {
    return MemoTagRemoteEntity(
        memoId = memoId,
        tagId = tagId,
        isMemoTag = isMemoTag,
        updatedAt = updatedAt,
    )
}

public fun MemoTagRemoteEntity.toLocal(): MemoTagLocalEntity {
    return MemoTagLocalEntity(
        memoId = memoId,
        tagId = tagId,
        isMemoTag = isMemoTag,
        updatedAt = updatedAt,
        serverUpdatedAt = updatedAt,
    )
}

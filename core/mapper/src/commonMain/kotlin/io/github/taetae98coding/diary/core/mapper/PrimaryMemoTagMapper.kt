package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.database.entity.PrimaryMemoTagLocalEntity
import io.github.taetae98coding.diary.core.entity.memo.PrimaryMemoTag

public fun PrimaryMemoTagLocalEntity.toEntity(): PrimaryMemoTag {
    return PrimaryMemoTag(
        isPrimary = isPrimary,
        tag = tag.toEntity(),
    )
}

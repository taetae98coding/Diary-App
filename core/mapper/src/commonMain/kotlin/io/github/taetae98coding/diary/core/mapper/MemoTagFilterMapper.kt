package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.database.entity.MemoTagFilterLocalEntity
import io.github.taetae98coding.diary.core.entity.tag.MemoTagFilter

public fun MemoTagFilterLocalEntity.toEntity(): MemoTagFilter {
    return MemoTagFilter(
        isFilter = isFilter,
        tag = tag.toEntity(),
    )
}

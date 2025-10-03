package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.database.entity.TagFilterLocalEntity
import io.github.taetae98coding.diary.core.entity.tag.TagFilter

public fun TagFilterLocalEntity.toEntity(): TagFilter {
    return TagFilter(
        isFilter = isFilter,
        tag = tag.toEntity(),
    )
}

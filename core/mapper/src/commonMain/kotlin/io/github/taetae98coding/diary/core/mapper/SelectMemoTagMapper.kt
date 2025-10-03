package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.database.entity.SelectMemoTagLocalEntity
import io.github.taetae98coding.diary.core.entity.tag.SelectMemoTag

public fun SelectMemoTagLocalEntity.toEntity(): SelectMemoTag {
    return SelectMemoTag(
        isSelected = isSelected,
        tag = tag.toEntity(),
    )
}

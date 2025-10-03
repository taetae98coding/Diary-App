package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.database.entity.SelectMemoTagLocalEntity
import io.github.taetae98coding.diary.core.entity.tag.SelectMemoTag
import io.github.taetae98coding.diary.core.service.entity.tag.SelectMemoTagRemoteEntity

public fun SelectMemoTagLocalEntity.toEntity(): SelectMemoTag {
    return SelectMemoTag(
        isSelected = isSelected,
        tag = tag.toEntity(),
    )
}

public fun SelectMemoTagRemoteEntity.toLocal(): SelectMemoTagLocalEntity {
    return SelectMemoTagLocalEntity(
        isSelected = isSelected,
        tag = tag.toLocal(),
    )
}

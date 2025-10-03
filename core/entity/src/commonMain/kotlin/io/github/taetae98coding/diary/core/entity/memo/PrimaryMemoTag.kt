package io.github.taetae98coding.diary.core.entity.memo

import io.github.taetae98coding.diary.core.entity.tag.Tag

public data class PrimaryMemoTag(
    val isPrimary: Boolean,
    val tag: Tag,
)

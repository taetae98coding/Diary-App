package io.github.taetae98coding.diary.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded

public data class SelectMemoTagLocalEntity(
    @ColumnInfo("isSelected")
    val isSelected: Boolean,
    @Embedded
    val tag: TagLocalEntity,
)

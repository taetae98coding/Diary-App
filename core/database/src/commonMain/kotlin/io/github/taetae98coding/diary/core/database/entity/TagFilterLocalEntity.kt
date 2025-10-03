package io.github.taetae98coding.diary.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded

public data class TagFilterLocalEntity(
    @ColumnInfo("isFilter")
    val isFilter: Boolean,
    @Embedded
    val tag: TagLocalEntity,
)

package io.github.taetae98coding.diary.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded

public data class PrimaryMemoTagLocalEntity(
    @ColumnInfo("isPrimary")
    val isPrimary: Boolean,
    @Embedded
    val tag: TagLocalEntity,
)

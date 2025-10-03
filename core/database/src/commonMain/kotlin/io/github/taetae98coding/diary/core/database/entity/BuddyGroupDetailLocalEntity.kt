package io.github.taetae98coding.diary.core.database.entity

import androidx.room.ColumnInfo

public data class BuddyGroupDetailLocalEntity(
    @ColumnInfo(defaultValue = "")
    val title: String,
    @ColumnInfo(defaultValue = "")
    val description: String,
)

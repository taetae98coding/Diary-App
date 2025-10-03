package io.github.taetae98coding.diary.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.Uuid

@Entity(tableName = "BuddyGroup")
public data class BuddyGroupLocalEntity(
    @PrimaryKey
    @ColumnInfo(defaultValue = "00000000-0000-0000-0000-000000000000")
    val id: Uuid,
    @Embedded
    val detail: BuddyGroupDetailLocalEntity,
)

package io.github.taetae98coding.diary.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlin.uuid.Uuid

@Entity(
    tableName = "MemoListTagFilter",
    foreignKeys = [
        ForeignKey(
            entity = TagLocalEntity::class,
            parentColumns = ["id"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ],
)
public data class MemoListTagFilterLocalEntity(
    @PrimaryKey
    @ColumnInfo(defaultValue = "00000000-0000-0000-0000-000000000000")
    val tagId: Uuid,
    @ColumnInfo(defaultValue = "0")
    val isFilter: Boolean,
)

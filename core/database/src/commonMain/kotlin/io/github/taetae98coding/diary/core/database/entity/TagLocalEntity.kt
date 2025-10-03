package io.github.taetae98coding.diary.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.Uuid

@Entity(tableName = "Tag")
public data class TagLocalEntity(
    @PrimaryKey
    @ColumnInfo(defaultValue = "00000000-0000-0000-0000-000000000000")
    val id: Uuid,
    @Embedded
    val detail: TagDetailLocalEntity,
    @ColumnInfo(defaultValue = "0")
    val isFinished: Boolean,
    @ColumnInfo(defaultValue = "0")
    val isDeleted: Boolean,
    @ColumnInfo(defaultValue = "0")
    val updatedAt: Long,
    @ColumnInfo(defaultValue = "0")
    val createdAt: Long,
    @ColumnInfo(defaultValue = "-1")
    val serverUpdatedAt: Long,
)

package io.github.taetae98coding.diary.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlin.uuid.Uuid

@Entity(
    tableName = "Memo",
    indices = [
        Index(value = ["primaryTag"]),
    ],
    foreignKeys = [
        ForeignKey(
            entity = TagLocalEntity::class,
            parentColumns = ["id"],
            childColumns = ["primaryTag"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE,
        ),
    ],
)
public data class MemoLocalEntity(
    @PrimaryKey
    @ColumnInfo(defaultValue = "00000000-0000-0000-0000-000000000000")
    val id: Uuid,
    @Embedded
    val detail: MemoDetailLocalEntity,
    @ColumnInfo(defaultValue = "null")
    val primaryTag: Uuid?,
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

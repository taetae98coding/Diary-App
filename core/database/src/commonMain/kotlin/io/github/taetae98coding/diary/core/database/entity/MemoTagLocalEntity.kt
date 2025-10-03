package io.github.taetae98coding.diary.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlin.uuid.Uuid

@Entity(
    tableName = "MemoTag",
    indices = [Index(value = ["memoId"]), Index(value = ["tagId"])],
    primaryKeys = ["memoId", "tagId"],
    foreignKeys = [
        ForeignKey(
            entity = MemoLocalEntity::class,
            parentColumns = ["id"],
            childColumns = ["memoId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = TagLocalEntity::class,
            parentColumns = ["id"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ],
)
public data class MemoTagLocalEntity(
    @ColumnInfo(defaultValue = "00000000-0000-0000-0000-000000000000")
    val memoId: Uuid,
    @ColumnInfo(defaultValue = "00000000-0000-0000-0000-000000000000")
    val tagId: Uuid,
    @ColumnInfo(defaultValue = "0")
    val isMemoTag: Boolean,
    @ColumnInfo(defaultValue = "0")
    val updatedAt: Long,
    @ColumnInfo(defaultValue = "-1")
    val serverUpdatedAt: Long,
)

package io.github.taetae98coding.diary.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlin.uuid.Uuid

@Entity(
    tableName = "BuddyGroupMemo",
    indices = [Index(value = ["buddyGroupId"]), Index(value = ["memoId"])],
    primaryKeys = ["buddyGroupId", "memoId"],
    foreignKeys = [
        ForeignKey(
            entity = BuddyGroupLocalEntity::class,
            parentColumns = ["id"],
            childColumns = ["buddyGroupId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = MemoLocalEntity::class,
            parentColumns = ["id"],
            childColumns = ["memoId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ],
)
internal data class BuddyGroupMemoLocalEntity(
    @ColumnInfo(defaultValue = "00000000-0000-0000-0000-000000000000")
    val buddyGroupId: Uuid,
    @ColumnInfo(defaultValue = "00000000-0000-0000-0000-000000000000")
    val memoId: Uuid,
)

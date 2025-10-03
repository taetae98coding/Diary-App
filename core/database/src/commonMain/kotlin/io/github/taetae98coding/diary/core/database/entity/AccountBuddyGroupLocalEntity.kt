package io.github.taetae98coding.diary.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlin.uuid.Uuid

@Entity(
    tableName = "AccountBuddyGroup",
    indices = [Index(value = ["buddyGroupId"])],
    primaryKeys = ["accountId", "buddyGroupId"],
    foreignKeys = [
        ForeignKey(
            entity = BuddyGroupLocalEntity::class,
            parentColumns = ["id"],
            childColumns = ["buddyGroupId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ],
)
internal data class AccountBuddyGroupLocalEntity(
    @ColumnInfo(defaultValue = "00000000-0000-0000-0000-000000000000")
    val accountId: Uuid,
    @ColumnInfo(defaultValue = "00000000-0000-0000-0000-000000000000")
    val buddyGroupId: Uuid,
)

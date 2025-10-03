package io.github.taetae98coding.diary.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlin.uuid.Uuid

@Entity(
    tableName = "AccountTag",
    indices = [Index(value = ["tagId"])],
    primaryKeys = ["accountId", "tagId"],
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
internal data class AccountTagLocalEntity(
    @ColumnInfo(defaultValue = "00000000-0000-0000-0000-000000000000")
    val accountId: Uuid,
    @ColumnInfo(defaultValue = "00000000-0000-0000-0000-000000000000")
    val tagId: Uuid,
)

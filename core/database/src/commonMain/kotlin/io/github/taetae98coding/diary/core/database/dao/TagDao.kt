package io.github.taetae98coding.diary.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class TagDao : RoomDao<TagLocalEntity>() {
    @Query(
        """
        UPDATE Tag
        SET title = :title,
            description = :description,
            color = :color,
            updatedAt = :updatedAt
        WHERE id = :tagId
    """,
    )
    abstract suspend fun update(
        tagId: Uuid,
        title: String,
        description: String,
        color: Int,
        updatedAt: Long,
    )

    @Query(
        """
        UPDATE Tag
        SET isFinished = :isFinished,
            updatedAt = :updatedAt
        WHERE id = :tagId
    """,
    )
    abstract suspend fun updateFinished(tagId: Uuid, isFinished: Boolean, updatedAt: Long)

    @Query(
        """
        UPDATE Tag
        SET isDeleted = :isDeleted,
            updatedAt = :updatedAt
        WHERE id = :tagId
    """,
    )
    abstract suspend fun updateDeleted(tagId: Uuid, isDeleted: Boolean, updatedAt: Long)

    @Query("SELECT * FROM Tag WHERE id = :tagId")
    abstract fun get(tagId: Uuid): Flow<TagLocalEntity?>
}

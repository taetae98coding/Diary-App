package io.github.taetae98coding.diary.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

@Dao
internal abstract class MemoDao : RoomDao<MemoLocalEntity>() {
    @Query(
        """
        UPDATE Memo
        SET title = :title,
            description = :description,
            start = :start,
            endInclusive = :endInclusive,
            color = :color,
            updatedAt = :updatedAt
        WHERE id = :memoId
    """,
    )
    abstract suspend fun update(
        memoId: Uuid,
        title: String,
        description: String,
        start: LocalDate?,
        endInclusive: LocalDate?,
        color: Int,
        updatedAt: Long,
    )

    @Query(
        """
        UPDATE Memo
        SET isFinished = :isFinished,
            updatedAt = :updatedAt
        WHERE id = :memoId
    """,
    )
    abstract suspend fun updateFinished(memoId: Uuid, isFinished: Boolean, updatedAt: Long)

    @Query(
        """
        UPDATE Memo
        SET isDeleted = :isDeleted,
            updatedAt = :updatedAt
        WHERE id = :memoId
    """,
    )
    abstract suspend fun updateDeleted(memoId: Uuid, isDeleted: Boolean, updatedAt: Long)

    @Query(
        """
        UPDATE Memo
        SET primaryTag = :tagId,
            updatedAt = :updatedAt
        WHERE id = :memoId
    """,
    )
    abstract suspend fun updatePrimaryTag(memoId: Uuid, tagId: Uuid?, updatedAt: Long)

    @Query("SELECT * FROM Memo WHERE id = :memoId")
    abstract fun get(memoId: Uuid): Flow<MemoLocalEntity?>
}

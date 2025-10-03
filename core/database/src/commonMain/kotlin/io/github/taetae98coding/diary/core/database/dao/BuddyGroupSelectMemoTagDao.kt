package io.github.taetae98coding.diary.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.SelectMemoTagLocalEntity
import kotlin.uuid.Uuid

@Dao
internal abstract class BuddyGroupSelectMemoTagDao {
    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
            SELECT
                Tag.*,
                COALESCE(MemoTag.isMemoTag, 0) AS isSelected
            FROM Tag
            INNER JOIN BuddyGroupTag ON BuddyGroupTag.tagId = Tag.id AND BuddyGroupTag.buddyGroupId = :buddyGroupId
            LEFT JOIN MemoTag ON MemoTag.tagId = Tag.id AND MemoTag.memoId = :memoId
            WHERE Tag.isDeleted = 0
            AND (Tag.isFinished = 0 OR MemoTag.isMemoTag)
            ORDER BY Tag.title
        """,
    )
    abstract fun page(buddyGroupId: Uuid, memoId: Uuid): PagingSource<Int, SelectMemoTagLocalEntity>
}

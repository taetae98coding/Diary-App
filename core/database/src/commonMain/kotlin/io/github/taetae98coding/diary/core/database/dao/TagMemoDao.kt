package io.github.taetae98coding.diary.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import kotlin.uuid.Uuid

@Dao
internal abstract class TagMemoDao {
    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT *
        FROM Memo
        INNER JOIN MemoTag ON MemoTag.memoId = Memo.id AND MemoTag.tagId = :tagId AND MemoTag.isMemoTag = 1
        WHERE isDeleted = 0
        AND isFinished = 0
        ORDER BY start, title
        """,
    )
    abstract fun page(tagId: Uuid): PagingSource<Int, MemoLocalEntity>
}

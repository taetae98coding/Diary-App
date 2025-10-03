package io.github.taetae98coding.diary.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import kotlin.uuid.Uuid

@Dao
internal abstract class MemoListDao {
    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT *
        FROM Memo
        INNER JOIN AccountMemo ON AccountMemo.memoId = Memo.id AND AccountMemo.accountId = :accountId
        WHERE Memo.isFinished = 0
        AND Memo.isDeleted = 0
        AND (
                (
                    SELECT COUNT(*)
                    FROM MemoListTagFilter
                    INNER JOIN AccountTag ON AccountTag.tagId = MemoListTagFilter.tagId AND AccountTag.accountId = :accountId
                    INNER JOIN Tag ON Tag.id = MemoListTagFilter.tagId AND Tag.isFinished = 0 AND Tag.isDeleted = 0
                    WHERE MemoListTagFilter.isFilter = 1
                ) = 0
            OR
                (
                    Memo.id IN (
                        SELECT MemoTag.memoId
                        FROM MemoTag
                        INNER JOIN MemoListTagFilter ON MemoListTagFilter.tagId = MemoTag.tagId AND MemoListTagFilter.isFilter = 1
                        INNER JOIN AccountTag ON AccountTag.tagId = MemoTag.tagId AND AccountTag.accountId = :accountId
                        INNER JOIN Tag ON Tag.id = MemoTag.tagId AND Tag.isFinished = 0 AND Tag.isDeleted = 0
                        WHERE MemoTag.isMemoTag
                    )
                )
        )
        ORDER BY Memo.start, Memo.title
    """,
    )
    abstract fun page(accountId: Uuid): PagingSource<Int, MemoLocalEntity>
}

package io.github.taetae98coding.diary.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import kotlin.uuid.Uuid

@Dao
internal abstract class ListMemoDao {
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
                    FROM ListMemoTagFilter
                    INNER JOIN AccountTag ON AccountTag.tagId = ListMemoTagFilter.tagId AND AccountTag.accountId = :accountId
                    WHERE ListMemoTagFilter.isFilter = 1
                ) = 0
            OR
                (
                    Memo.id IN (
                        SELECT MemoTag.memoId
                        FROM MemoTag
                        INNER JOIN ListMemoTagFilter ON ListMemoTagFilter.tagId = MemoTag.tagId AND ListMemoTagFilter.isFilter = 1
                        WHERE MemoTag.isMemoTag
                    )
                )
        )
        ORDER BY Memo.start, Memo.title
    """,
    )
    abstract fun page(accountId: Uuid): PagingSource<Int, MemoLocalEntity>
}

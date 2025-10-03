package io.github.taetae98coding.diary.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import kotlin.uuid.Uuid

@Dao
internal abstract class FinishedMemoListDao {
    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT *
        FROM Memo
        INNER JOIN AccountMemo ON AccountMemo.memoId = Memo.id AND AccountMemo.accountId = :accountId
        WHERE Memo.isFinished = 1
        AND Memo.isDeleted = 0
        ORDER BY Memo.start, Memo.title
    """,
    )
    abstract fun page(accountId: Uuid): PagingSource<Int, MemoLocalEntity>
}

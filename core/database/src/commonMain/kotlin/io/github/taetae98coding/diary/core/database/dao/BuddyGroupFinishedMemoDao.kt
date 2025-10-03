package io.github.taetae98coding.diary.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import kotlin.uuid.Uuid

@Dao
internal abstract class BuddyGroupFinishedMemoDao {
    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT *
        FROM Memo
        INNER JOIN BuddyGroupMemo ON BuddyGroupMemo.memoId = Memo.id AND BuddyGroupMemo.buddyGroupId = :buddyGroupId
        WHERE Memo.isFinished = 1
        AND Memo.isDeleted = 0
        ORDER BY Memo.start, Memo.title
    """,
    )
    abstract fun page(buddyGroupId: Uuid): PagingSource<Int, MemoLocalEntity>
}

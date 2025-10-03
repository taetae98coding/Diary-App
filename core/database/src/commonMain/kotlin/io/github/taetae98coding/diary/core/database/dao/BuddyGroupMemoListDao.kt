package io.github.taetae98coding.diary.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupMemoLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid

@Dao
internal abstract class BuddyGroupMemoListDao : RoomDao<BuddyGroupMemoLocalEntity>() {

    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT *
        FROM Memo
        INNER JOIN BuddyGroupMemo ON BuddyGroupMemo.memoId = Memo.id AND BuddyGroupMemo.buddyGroupId = :buddyGroupId
        WHERE Memo.isFinished = 0
        AND Memo.isDeleted = 0
                AND (
                (
                    SELECT COUNT(*)
                    FROM MemoListTagFilter
                    INNER JOIN BuddyGroupTag ON BuddyGroupTag.tagId = MemoListTagFilter.tagId AND BuddyGroupTag.buddyGroupId = :buddyGroupId
                    WHERE MemoListTagFilter.isFilter = 1
                ) = 0
            OR
                (
                    Memo.id IN (
                        SELECT MemoTag.memoId
                        FROM MemoTag
                        INNER JOIN MemoListTagFilter ON MemoListTagFilter.tagId = MemoTag.tagId AND MemoListTagFilter.isFilter = 1
                        INNER JOIN BuddyGroupTag ON BuddyGroupTag.tagId = MemoTag.tagId AND BuddyGroupTag.buddyGroupId = :buddyGroupId
                        WHERE MemoTag.isMemoTag
                    )
                )
        )
        ORDER BY Memo.start, Memo.title
    """,
    )
    abstract fun page(buddyGroupId: Uuid): PagingSource<Int, MemoLocalEntity>
}

package io.github.taetae98coding.diary.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupMemoLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid
import kotlinx.datetime.LocalDate

@Dao
internal abstract class BuddyGroupMemoDao : RoomDao<BuddyGroupMemoLocalEntity>() {
    @Query(
        """
        DELETE FROM BuddyGroupMemo
        WHERE buddyGroupId = :buddyGroupId
    """,
    )
    abstract suspend fun delete(buddyGroupId: Uuid)

    @Query(
        """
        DELETE FROM BuddyGroupMemo
        WHERE BuddyGroupMemo.buddyGroupId = :buddyGroupId
        AND BuddyGroupMemo.memoId IN (
            SELECT id
            FROM Memo
            WHERE (Memo.start <= :endInclusive AND :start <= Memo.endInclusive)
        )
    """,
    )
    abstract suspend fun deleteByDateRange(buddyGroupId: Uuid, start: LocalDate, endInclusive: LocalDate)

    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT *
        FROM Memo
        INNER JOIN BuddyGroupMemo ON BuddyGroupMemo.memoId = Memo.id AND BuddyGroupMemo.buddyGroupId = :buddyGroupId
        WHERE Memo.isFinished = 0
        AND Memo.isDeleted = 0
        ORDER BY Memo.start, Memo.title
    """,
    )
    abstract fun page(buddyGroupId: Uuid): PagingSource<Int, MemoLocalEntity>
}

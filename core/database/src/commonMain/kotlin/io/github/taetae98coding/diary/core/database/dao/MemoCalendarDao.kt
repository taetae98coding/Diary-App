package io.github.taetae98coding.diary.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.CalendarMemoLocalEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

@Dao
internal abstract class MemoCalendarDao {
    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT
            Memo.*,
            COALESCE(Tag.color, Memo.color) AS calendarColor
        FROM Memo
        INNER JOIN AccountMemo ON AccountMemo.memoId = Memo.id AND AccountMemo.accountId = :accountId
        LEFT JOIN Tag ON Tag.id = Memo.primaryTag AND Tag.isDeleted = 0
        WHERE Memo.isDeleted = 0
        AND (Memo.start <= :endInclusive AND :start <= Memo.endInclusive)
        AND (
                (
                    SELECT COUNT(*)
                    FROM MemoCalendarTagFilter
                    INNER JOIN AccountTag ON AccountTag.tagId = MemoCalendarTagFilter.tagId AND AccountTag.accountId = :accountId
                    INNER JOIN Tag ON Tag.id = MemoCalendarTagFilter.tagId AND Tag.isFinished = 0 AND Tag.isDeleted = 0
                    WHERE MemoCalendarTagFilter.isFilter = 1
                ) = 0
            OR
                (
                    Memo.id IN (
                        SELECT MemoTag.memoId
                        FROM MemoTag
                        INNER JOIN MemoCalendarTagFilter ON MemoCalendarTagFilter.tagId = MemoTag.tagId AND MemoCalendarTagFilter.isFilter = 1
                        INNER JOIN AccountTag ON AccountTag.tagId = MemoTag.tagId AND AccountTag.accountId = :accountId
                        INNER JOIN Tag ON Tag.id = MemoTag.tagId AND Tag.isFinished = 0 AND Tag.isDeleted = 0
                        WHERE MemoTag.isMemoTag
                    )
                )
        )
        ORDER BY Memo.start, Memo.title
    """,
    )
    abstract fun get(accountId: Uuid, start: LocalDate, endInclusive: LocalDate): Flow<List<CalendarMemoLocalEntity>>
}

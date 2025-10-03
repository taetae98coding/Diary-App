package io.github.taetae98coding.diary.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.MemoCalendarTagFilterLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagFilterLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class MemoCalendarTagFilterDao : RoomDao<MemoCalendarTagFilterLocalEntity>() {
    @Query(
        """
                SELECT COUNT(*) != 0
                FROM MemoCalendarTagFilter
                INNER JOIN AccountTag ON AccountTag.tagId = MemoCalendarTagFilter.tagId AND AccountTag.accountId = :accountId
                INNER JOIN Tag ON Tag.id = MemoCalendarTagFilter.tagId AND Tag.isFinished = 0 AND Tag.isDeleted = 0
                WHERE MemoCalendarTagFilter.isFilter
        """,
    )
    abstract fun hasFilter(accountId: Uuid): Flow<Boolean>

    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT
            Tag.*,
            COALESCE(MemoCalendarTagFilter.isFilter, 0) AS isFilter
        FROM Tag
        INNER JOIN AccountTag ON AccountTag.tagId = Tag.id AND AccountTag.accountId = :accountId
        LEFT JOIN MemoCalendarTagFilter ON MemoCalendarTagFilter.tagId = Tag.id
        WHERE Tag.isFinished = 0
        AND Tag.isDeleted = 0
        ORDER BY Tag.title
    """,
    )
    abstract fun page(accountId: Uuid): PagingSource<Int, TagFilterLocalEntity>
}

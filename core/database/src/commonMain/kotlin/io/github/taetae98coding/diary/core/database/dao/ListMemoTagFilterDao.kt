package io.github.taetae98coding.diary.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.ListMemoTagFilterLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoTagFilterLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class ListMemoTagFilterDao : RoomDao<ListMemoTagFilterLocalEntity>() {
    @Query(
        """
                SELECT COUNT(*) != 0
                FROM ListMemoTagFilter
                INNER JOIN AccountTag ON AccountTag.tagId = ListMemoTagFilter.tagId AND AccountTag.accountId = :accountId
                WHERE ListMemoTagFilter.isFilter
        """,
    )
    abstract fun hasFilter(accountId: Uuid): Flow<Boolean>

    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT
            Tag.*,
            COALESCE(ListMemoTagFilter.isFilter, 0) AS isFilter
        FROM Tag
        INNER JOIN AccountTag ON AccountTag.tagId = Tag.id AND AccountTag.accountId = :accountId
        LEFT JOIN ListMemoTagFilter ON ListMemoTagFilter.tagId = Tag.id
        WHERE Tag.isFinished = 0
        AND Tag.isDeleted = 0
        ORDER BY Tag.title
    """,
    )
    abstract fun page(accountId: Uuid): PagingSource<Int, MemoTagFilterLocalEntity>
}

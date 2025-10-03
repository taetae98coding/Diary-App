package io.github.taetae98coding.diary.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.AccountTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class AccountTagDao : RoomDao<AccountTagLocalEntity>() {
    @Query(
        """
        SELECT MAX(serverUpdatedAt)
        FROM Tag
        INNER JOIN AccountTag ON AccountTag.tagId = Tag.id AND AccountTag.accountId = :accountId
        """,
    )
    abstract fun getLastUpdatedAt(accountId: Uuid): Flow<Long?>

    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT *
        FROM Tag
        INNER JOIN AccountTag ON AccountTag.tagId=Tag.id AND AccountTag.accountId = :accountId
        WHERE Tag.isFinished = 0
        AND Tag.isDeleted = 0
        ORDER BY Tag.title
    """,
    )
    abstract fun page(accountId: Uuid): PagingSource<Int, TagLocalEntity>
}

package io.github.taetae98coding.diary.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import kotlin.uuid.Uuid

@Dao
internal abstract class FinishedTagDao {
    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT *
        FROM Tag
        INNER JOIN AccountTag ON AccountTag.tagId=Tag.id AND AccountTag.accountId = :accountId
        WHERE Tag.isFinished = 1
        AND Tag.isDeleted = 0
        ORDER BY Tag.title
    """,
    )
    abstract fun page(accountId: Uuid): PagingSource<Int, TagLocalEntity>
}

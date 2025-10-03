package io.github.taetae98coding.diary.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import kotlin.uuid.Uuid

@Dao
internal abstract class MemoSearchDao {
    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT *
        FROM Memo
        INNER JOIN AccountMemo ON AccountMemo.memoId = Memo.id AND AccountMemo.accountId = :accountId
        WHERE Memo.isDeleted = 0
        AND (Memo.title LIKE :keyword OR Memo.description LIKE :keyword)
        ORDER BY Memo.updatedAt DESC, Memo.title
        """,
    )
    abstract fun search(accountId: Uuid, keyword: String): PagingSource<Int, MemoLocalEntity>
}


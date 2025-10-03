package io.github.taetae98coding.diary.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.BackupMemoTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoTagLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class BackupMemoTagDao : RoomDao<BackupMemoTagLocalEntity>() {
    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT MemoTag.*
        FROM MemoTag
        INNER JOIN BackupMemoTag ON MemoTag.memoId = BackupMemoTag.memoId AND MemoTag.tagId = BackupMemoTag.tagId
        INNER JOIN AccountMemo ON AccountMemo.memoId = BackupMemoTag.memoId AND AccountMemo.accountId = :accountId
        INNER JOIN AccountTag ON AccountTag.tagId = BackupMemoTag.tagId AND AccountTag.accountId = :accountId
        ORDER BY MemoTag.updatedAt
        LIMIT 100
        """,
    )
    abstract fun get(accountId: Uuid): Flow<List<MemoTagLocalEntity>>
}

package io.github.taetae98coding.diary.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.BackupMemoLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class BackupMemoDao : RoomDao<BackupMemoLocalEntity>() {
    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT *
        FROM Memo
        INNER JOIN BackupMemo ON BackupMemo.memoId = Memo.id AND BackupMemo.accountId = :accountId
        ORDER BY Memo.updatedAt
        LIMIT 100
        """,
    )
    abstract fun get(accountId: Uuid): Flow<List<MemoLocalEntity>>
}

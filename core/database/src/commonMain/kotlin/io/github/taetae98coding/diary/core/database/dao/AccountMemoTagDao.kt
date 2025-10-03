package io.github.taetae98coding.diary.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.taetae98coding.diary.core.database.entity.AccountMemoLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class AccountMemoTagDao : RoomDao<AccountMemoLocalEntity>() {
    @Query(
        """
        SELECT MAX(serverUpdatedAt)
        FROM MemoTag
        INNER JOIN AccountMemo ON AccountMemo.memoId = MemoTag.memoId AND AccountMemo.accountId = :accountId
        INNER JOIN AccountTag ON AccountTag.tagId = MemoTag.tagId AND AccountTag.accountId = :accountId
        """,
    )
    abstract fun getLastUpdatedAt(accountId: Uuid): Flow<Long?>
}

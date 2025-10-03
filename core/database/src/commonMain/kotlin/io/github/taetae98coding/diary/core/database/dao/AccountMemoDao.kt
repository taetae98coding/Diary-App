package io.github.taetae98coding.diary.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.taetae98coding.diary.core.database.entity.AccountMemoLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class AccountMemoDao : RoomDao<AccountMemoLocalEntity>() {
    @Query(
        """
        SELECT MAX(serverUpdatedAt)
        FROM Memo
        INNER JOIN AccountMemo ON AccountMemo.memoId = Memo.id AND AccountMemo.accountId = :accountId
        """,
    )
    abstract fun getLastUpdatedAt(accountId: Uuid): Flow<Long?>
}

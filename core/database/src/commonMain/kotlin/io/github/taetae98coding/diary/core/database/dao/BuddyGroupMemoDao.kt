package io.github.taetae98coding.diary.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupMemoLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class BuddyGroupMemoDao : RoomDao<BuddyGroupMemoLocalEntity>() {
    @Query(
        """
        SELECT MAX(Memo.updatedAt)
        FROM Memo
        INNER JOIN BuddyGroupMemo ON BuddyGroupMemo.memoId = Memo.id AND BuddyGroupMemo.buddyGroupId = :buddyGroupId
    """,
    )
    abstract fun getLastUpdateAt(buddyGroupId: Uuid): Flow<Long?>
}

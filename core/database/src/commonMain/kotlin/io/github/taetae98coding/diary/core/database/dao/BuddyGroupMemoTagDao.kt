package io.github.taetae98coding.diary.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class BuddyGroupMemoTagDao {
    @Query(
        """
        SELECT MAX(MemoTag.updatedAt)
        FROM MemoTag
        INNER JOIN BuddyGroupMemo ON BuddyGroupMemo.memoId = MemoTag.memoId AND BuddyGroupMemo.buddyGroupId = :buddyGroupId
        INNER JOIN BuddyGroupTag ON BuddyGroupTag.tagId = MemoTag.tagId AND BuddyGroupTag.buddyGroupId = :buddyGroupId
    """,
    )
    abstract fun getLastUpdateAt(buddyGroupId: Uuid): Flow<Long?>
}

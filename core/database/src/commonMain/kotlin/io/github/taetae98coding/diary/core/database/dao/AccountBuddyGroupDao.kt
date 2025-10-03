package io.github.taetae98coding.diary.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.taetae98coding.diary.core.database.entity.AccountBuddyGroupLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid

@Dao
internal abstract class AccountBuddyGroupDao : RoomDao<AccountBuddyGroupLocalEntity>() {
    @Query(
        """
        DELETE FROM AccountBuddyGroup
        WHERE accountId = :accountId
    """,
    )
    abstract suspend fun delete(accountId: Uuid)
}

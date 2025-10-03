package io.github.taetae98coding.diary.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class BuddyGroupDao : RoomDao<BuddyGroupLocalEntity>() {
    @Query(
        """
        SELECT *
        FROM BuddyGroup
        WHERE id = :buddyGroupId
        """,
    )
    abstract fun get(buddyGroupId: Uuid): Flow<BuddyGroupLocalEntity?>

    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT *
        FROM BuddyGroup
        INNER JOIN AccountBuddyGroup ON AccountBuddyGroup.buddyGroupId = BuddyGroup.id AND AccountBuddyGroup.accountId = :accountId
        ORDER BY title
    """,
    )
    abstract fun page(accountId: Uuid): PagingSource<Int, BuddyGroupLocalEntity>
}

package io.github.taetae98coding.diary.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class BuddyGroupTagDao : RoomDao<BuddyGroupTagLocalEntity>() {
    @Query(
        """
        SELECT MAX(Tag.updatedAt)
        FROM Tag
        INNER JOIN BuddyGroupTag ON BuddyGroupTag.tagId = Tag.id AND BuddyGroupTag.buddyGroupId = :buddyGroupId
    """,
    )
    abstract fun getLastUpdateAt(buddyGroupId: Uuid): Flow<Long?>

    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT *
        FROM Tag
        INNER JOIN BuddyGroupTag ON BuddyGroupTag.tagId = Tag.id AND BuddyGroupTag.buddyGroupId = :buddyGroupId
        WHERE Tag.isFinished = 0
        AND Tag.isDeleted = 0
        ORDER BY Tag.title
    """,
    )
    abstract fun page(buddyGroupId: Uuid): PagingSource<Int, TagLocalEntity>
}

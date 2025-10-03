package io.github.taetae98coding.diary.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid

@Dao
internal abstract class BuddyGroupTagDao : RoomDao<BuddyGroupTagLocalEntity>() {
    @Query(
        """
        DELETE FROM BuddyGroupTag
        WHERE buddyGroupId = :buddyGroupId
    """,
    )
    abstract suspend fun delete(buddyGroupId: Uuid)

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

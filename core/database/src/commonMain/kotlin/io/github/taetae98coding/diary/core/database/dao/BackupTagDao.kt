package io.github.taetae98coding.diary.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import io.github.taetae98coding.diary.core.database.entity.BackupTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class BackupTagDao : RoomDao<BackupTagLocalEntity>() {
    @RewriteQueriesToDropUnusedColumns
    @Query(
        """
        SELECT *
        FROM Tag
        INNER JOIN BackupTag ON BackupTag.tagId = Tag.id AND BackupTag.accountId = :accountId
        ORDER BY Tag.updatedAt
        LIMIT 100
        """,
    )
    abstract fun get(accountId: Uuid): Flow<List<TagLocalEntity>>
}

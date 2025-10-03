package io.github.taetae98coding.diary.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.taetae98coding.diary.core.database.entity.MemoTagLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlin.uuid.Uuid

@Dao
internal abstract class MemoTagDao : RoomDao<MemoTagLocalEntity>() {
    @Query("DELETE FROM MemoTag WHERE memoId = :memoId")
    abstract suspend fun delete(memoId: Uuid)
}

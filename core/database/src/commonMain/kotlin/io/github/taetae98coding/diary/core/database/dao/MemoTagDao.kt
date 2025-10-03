package io.github.taetae98coding.diary.core.database.dao

import androidx.room.Dao
import io.github.taetae98coding.diary.core.database.entity.MemoTagLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao

@Dao
internal abstract class MemoTagDao : RoomDao<MemoTagLocalEntity>()

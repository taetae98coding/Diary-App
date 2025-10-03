package io.github.taetae98coding.diary.library.room.common.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
public abstract class RoomDao<T> {
    @Transaction
    @Upsert
    public abstract suspend fun upsert(entity: Collection<T>)

    @Transaction
    @Delete
    public abstract suspend fun delete(entity: Collection<T>)
}

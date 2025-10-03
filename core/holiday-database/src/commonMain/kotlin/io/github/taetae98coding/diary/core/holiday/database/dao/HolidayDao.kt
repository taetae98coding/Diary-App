package io.github.taetae98coding.diary.core.holiday.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.taetae98coding.diary.core.holiday.database.entity.HolidayLocalEntity
import io.github.taetae98coding.diary.library.room.common.dao.RoomDao
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Month

@Dao
internal abstract class HolidayDao : RoomDao<HolidayLocalEntity>() {
    @Query(
        """
        DELETE FROM Holiday
        WHERE CAST(strftime('%Y', localDate) AS INTEGER) = :year
        AND CAST(strftime('%m', localDate) AS INTEGER) = :month
        """,
    )
    abstract suspend fun deleteByYearMonth(year: Int, month: Month)

    @Query(
        """
        SELECT *
        FROM Holiday
        WHERE CAST(strftime('%Y', localDate) AS INTEGER) = :year
        AND CAST(strftime('%m', localDate) AS INTEGER) = :month
        """,
    )
    abstract fun get(year: Int, month: Month): Flow<List<HolidayLocalEntity>>
}

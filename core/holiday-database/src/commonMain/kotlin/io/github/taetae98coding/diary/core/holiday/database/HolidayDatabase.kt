package io.github.taetae98coding.diary.core.holiday.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.taetae98coding.diary.core.holiday.database.dao.HolidayDao
import io.github.taetae98coding.diary.core.holiday.database.dao.SpecialDayDao
import io.github.taetae98coding.diary.core.holiday.database.entity.HolidayLocalEntity
import io.github.taetae98coding.diary.core.holiday.database.entity.SpecialDayLocalEntity
import io.github.taetae98coding.diary.library.room.common.converter.LocalDateConverter
import io.github.taetae98coding.diary.library.room.common.converter.MonthConverter

@Database(
    entities = [
        HolidayLocalEntity::class,
        SpecialDayLocalEntity::class,
    ],
    version = 1,
)
@TypeConverters(
    value = [
        MonthConverter::class,
        LocalDateConverter::class,
    ],
)
@ConstructedBy(HolidayDatabaseConstructor::class)
internal abstract class HolidayDatabase : RoomDatabase() {
    abstract fun holiday(): HolidayDao
    abstract fun specialDay(): SpecialDayDao
}

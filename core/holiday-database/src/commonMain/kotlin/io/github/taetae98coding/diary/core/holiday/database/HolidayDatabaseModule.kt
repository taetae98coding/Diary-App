package io.github.taetae98coding.diary.core.holiday.database

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import io.github.taetae98coding.diary.library.koin.room.roomDatabaseBuilder
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent

@Module
@ComponentScan
public class HolidayDatabaseModule : KoinComponent {
    @Single
    internal fun providesHolidayDatabase(): HolidayDatabase {
        return roomDatabaseBuilder<HolidayDatabase>("holiday.db")
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}

package io.github.taetae98coding.diary.core.holiday.database.datasource

import io.github.taetae98coding.diary.core.holiday.database.HolidayDatabase
import io.github.taetae98coding.diary.core.holiday.database.entity.SpecialDayLocalEntity
import io.github.taetae98coding.diary.core.holiday.database.transaction.HolidayDatabaseTransactor
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.YearMonth
import org.koin.core.annotation.Factory

@Factory
public class SpecialDayLocalDataSource internal constructor(
    private val database: HolidayDatabase,
    private val transactor: HolidayDatabaseTransactor,
) {
    public fun get(yearMonth: YearMonth): Flow<List<SpecialDayLocalEntity>> {
        return database.specialDay().get(yearMonth.year, yearMonth.month)
    }

    public suspend fun submit(yearMonth: YearMonth, entity: Collection<SpecialDayLocalEntity>) {
        transactor.immediate {
            database.specialDay().deleteByYearMonth(yearMonth.year, yearMonth.month)
            database.specialDay().upsert(entity)
        }
    }
}

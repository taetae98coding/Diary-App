package io.github.taetae98coding.diary.core.database.datasource

import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.CalendarMemoLocalEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateRange
import org.koin.core.annotation.Factory

@Factory
public class MemoCalendarLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public fun get(accountId: Uuid, dateRange: LocalDateRange): Flow<List<CalendarMemoLocalEntity>> {
        return database.memoCalendar().get(accountId, dateRange.start, dateRange.endInclusive)
    }
}

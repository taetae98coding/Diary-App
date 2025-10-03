package io.github.taetae98coding.diary.presenter.calendar.colortext

import io.github.taetae98coding.diary.core.entity.memo.CalendarMemo
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateRange
import kotlinx.datetime.YearMonth

public interface CalendarColorTextStrategy {
    public suspend fun fetchCalendarMemo(yearMonth: YearMonth): Result<Unit>
    public fun getCalendarMemo(dateRange: LocalDateRange): Flow<Result<List<CalendarMemo>>>
}

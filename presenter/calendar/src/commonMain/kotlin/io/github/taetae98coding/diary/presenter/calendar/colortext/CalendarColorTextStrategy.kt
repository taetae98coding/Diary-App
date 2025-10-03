package io.github.taetae98coding.diary.presenter.calendar.colortext

import io.github.taetae98coding.diary.core.entity.memo.CalendarMemo
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateRange

public interface CalendarColorTextStrategy {
    public suspend fun fetchCalendarMemo(dateRange: LocalDateRange): Result<Unit>
    public fun getCalendarMemo(dateRange: LocalDateRange): Flow<Result<List<CalendarMemo>>>
}

package io.github.taetae98coding.diary.presenter.calendar.holiday

import io.github.taetae98coding.diary.compose.calendar.item.CalendarTextItemUiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.YearMonth

public interface CalendarHolidayStateHolder {
    public val holidayUiState: StateFlow<List<CalendarTextItemUiState.Holiday>>
    public val specialDayUiState: StateFlow<List<CalendarTextItemUiState.SpecialDay>>

    public fun fetchHoliday(yearMonth: YearMonth)
}

package io.github.taetae98coding.diary.compose.calendar.month

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.taetae98coding.diary.compose.calendar.CalendarSelectState
import io.github.taetae98coding.diary.compose.calendar.rememberCalendarSelectState
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.YearMonth

public data class MonthState(
    internal val yearMonth: YearMonth,
    internal val startDayOfWeek: DayOfWeek,
    internal val selectState: CalendarSelectState,
)

@Composable
public fun rememberMonthState(
    yearMonth: YearMonth,
    startDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY,
    selectState: CalendarSelectState = rememberCalendarSelectState(),
): MonthState {
    return remember(
        yearMonth,
        startDayOfWeek,
        selectState,
    ) {
        MonthState(
            yearMonth = yearMonth,
            startDayOfWeek = startDayOfWeek,
            selectState = selectState,
        )
    }
}

package io.github.taetae98coding.diary.compose.calendar.week

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.taetae98coding.diary.compose.calendar.CalendarSelectState
import io.github.taetae98coding.diary.library.kotlinx.datetime.dayNumber
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.YearMonth
import kotlinx.datetime.minus
import kotlinx.datetime.plus

internal data class WeekOfMonthState(
    val yearMonth: YearMonth,
    val weekOfMonth: Int,
    val startDayOfWeek: DayOfWeek,
    val selectState: CalendarSelectState,
) {
    val dateRange by lazy {
        val start = yearMonth.firstDay.plus(weekOfMonth, DateTimeUnit.WEEK)
            .let { it.minus(dayNumber(it.dayOfWeek, startDayOfWeek), DateTimeUnit.DAY) }

        val endInclusive = start.plus(1, DateTimeUnit.WEEK)
            .minus(1, DateTimeUnit.DAY)

        start..endInclusive
    }
}

@Composable
internal fun rememberWeekOfMonthState(
    yearMonth: YearMonth,
    weekOfMonth: Int,
    startDayOfWeek: DayOfWeek,
    selectState: CalendarSelectState,
): WeekOfMonthState {
    return remember(
        yearMonth,
        weekOfMonth,
        startDayOfWeek,
        selectState,
    ) {
        WeekOfMonthState(
            yearMonth = yearMonth,
            weekOfMonth = weekOfMonth,
            startDayOfWeek = startDayOfWeek,
            selectState = selectState,
        )
    }
}

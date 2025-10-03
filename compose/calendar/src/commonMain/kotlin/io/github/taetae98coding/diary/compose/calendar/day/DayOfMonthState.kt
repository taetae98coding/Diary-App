package io.github.taetae98coding.diary.compose.calendar.day

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.taetae98coding.diary.library.kotlinx.datetime.dayNumber
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.YearMonth
import kotlinx.datetime.plus
import kotlinx.datetime.yearMonth

internal data class DayOfMonthState(
    val yearMonth: YearMonth,
    val weekOfMonth: Int,
    val dayOfWeek: DayOfWeek,
    val startDayOfWeek: DayOfWeek,
) {
    val localDate by lazy {
        yearMonth.firstDay.plus(weekOfMonth, DateTimeUnit.WEEK)
            .let { it.plus(dayNumber(dayOfWeek, startDayOfWeek) - dayNumber(it.dayOfWeek, startDayOfWeek), DateTimeUnit.DAY) }
    }

    val isInMonth: Boolean by lazy {
        localDate.yearMonth == yearMonth
    }
}

@Composable
internal fun rememberDayOfMonthState(
    yearMonth: YearMonth,
    weekOfMonth: Int,
    dayOfWeek: DayOfWeek,
    startDayOfWeek: DayOfWeek,
): DayOfMonthState {
    return remember(
        yearMonth,
        weekOfMonth,
        dayOfWeek,
        startDayOfWeek,
    ) {
        DayOfMonthState(
            yearMonth = yearMonth,
            weekOfMonth = weekOfMonth,
            dayOfWeek = dayOfWeek,
            startDayOfWeek = startDayOfWeek,
        )
    }
}

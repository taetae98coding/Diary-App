package io.github.taetae98coding.diary.compose.calendar.week

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.taetae98coding.diary.compose.calendar.CalendarDefaults
import io.github.taetae98coding.diary.compose.calendar.color.CalendarColors
import io.github.taetae98coding.diary.compose.calendar.day.DayOfMonth
import io.github.taetae98coding.diary.compose.calendar.day.rememberDayOfMonthState
import io.github.taetae98coding.diary.compose.calendar.item.CalendarTextItemUiState
import io.github.taetae98coding.diary.library.kotlinx.datetime.dayOfWeek
import kotlinx.datetime.LocalDate

@Composable
internal fun DayOfMonthRow(
    state: WeekOfMonthState,
    modifier: Modifier = Modifier,
    primaryDateProvider: () -> List<LocalDate> = { emptyList() },
    holidayProvider: () -> List<CalendarTextItemUiState.Holiday> = { emptyList() },
    colors: CalendarColors = CalendarDefaults.colors(),
) {
    Row(modifier = modifier) {
        repeat(7) { dayNumber ->
            val dayOfWeekState = rememberDayOfMonthState(
                yearMonth = state.yearMonth,
                weekOfMonth = state.weekOfMonth,
                dayOfWeek = dayOfWeek(dayNumber, state.startDayOfWeek),
                startDayOfWeek = state.startDayOfWeek,
            )

            DayOfMonth(
                state = dayOfWeekState,
                modifier = Modifier.weight(1f),
                primaryDateProvider = primaryDateProvider,
                holidayProvider = holidayProvider,
                colors = colors,
            )
        }
    }
}

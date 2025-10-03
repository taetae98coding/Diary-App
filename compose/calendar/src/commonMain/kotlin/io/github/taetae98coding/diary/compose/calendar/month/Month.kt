package io.github.taetae98coding.diary.compose.calendar.month

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.taetae98coding.diary.compose.calendar.CalendarDefaults
import io.github.taetae98coding.diary.compose.calendar.color.CalendarColors
import io.github.taetae98coding.diary.compose.calendar.item.CalendarTextItemUiState
import io.github.taetae98coding.diary.compose.calendar.item.CalendarWeatherUiState
import io.github.taetae98coding.diary.compose.calendar.week.WeekOfMonth
import io.github.taetae98coding.diary.compose.calendar.week.rememberWeekOfMonthState
import kotlinx.datetime.LocalDate

@Composable
public fun Month(
    state: MonthState,
    modifier: Modifier = Modifier,
    primaryDateProvider: () -> List<LocalDate> = { emptyList() },
    colorTextProvider: () -> List<CalendarTextItemUiState.ColorText> = { emptyList() },
    weatherProvider: () -> List<CalendarWeatherUiState> = { emptyList() },
    holidayProvider: () -> List<CalendarTextItemUiState.Holiday> = { emptyList() },
    specialDayProvider: () -> List<CalendarTextItemUiState.SpecialDay> = { emptyList() },
    onColorTextClick: ((Any) -> Unit)? = null,
    colors: CalendarColors = CalendarDefaults.colors(),
) {
    Column(modifier = modifier) {
        repeat(6) { weekOfMonth ->
            val weekOfMonthState = rememberWeekOfMonthState(
                yearMonth = state.yearMonth,
                weekOfMonth = weekOfMonth,
                startDayOfWeek = state.startDayOfWeek,
                selectState = state.selectState,
            )

            WeekOfMonth(
                state = weekOfMonthState,
                modifier = Modifier.weight(1F),
                primaryDateProvider = primaryDateProvider,
                colorTextProvider = colorTextProvider,
                weatherProvider = weatherProvider,
                holidayProvider = holidayProvider,
                specialDayProvider = specialDayProvider,
                onColorTextClick = onColorTextClick,
                colors = colors,
            )
        }
    }
}

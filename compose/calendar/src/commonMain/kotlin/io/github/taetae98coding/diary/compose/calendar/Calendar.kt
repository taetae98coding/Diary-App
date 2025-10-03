package io.github.taetae98coding.diary.compose.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.taetae98coding.diary.compose.calendar.color.CalendarColors
import io.github.taetae98coding.diary.compose.calendar.internal.yearMonth
import io.github.taetae98coding.diary.compose.calendar.item.CalendarTextItemUiState
import io.github.taetae98coding.diary.compose.calendar.item.CalendarWeatherUiState
import io.github.taetae98coding.diary.compose.calendar.modifier.calendarSelectable
import io.github.taetae98coding.diary.compose.calendar.month.Month
import io.github.taetae98coding.diary.compose.calendar.month.rememberMonthState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateRange

@Composable
public fun Calendar(
    state: CalendarState,
    modifier: Modifier = Modifier,
    primaryDateProvider: () -> List<LocalDate> = { emptyList() },
    colorTextProvider: () -> List<CalendarTextItemUiState.ColorText> = { emptyList() },
    weatherProvider: () -> List<CalendarWeatherUiState> = { emptyList() },
    holidayProvider: () -> List<CalendarTextItemUiState.Holiday> = { emptyList() },
    specialDayProvider: () -> List<CalendarTextItemUiState.SpecialDay> = { emptyList() },
    onDrag: ((LocalDateRange) -> Unit)? = null,
    onColorTextClick: ((Any) -> Unit)? = null,
    colors: CalendarColors = CalendarDefaults.colors(),
) {
    Column(modifier = modifier) {
        DayOfWeekRow(
            state = state,
            colors = colors,
        )
        HorizontalPager(
            state = state.pagerState,
            modifier = if (onDrag == null) {
                Modifier
            } else {
                Modifier.calendarSelectable(state = state, onDragFinish = onDrag)
            },
        ) { page ->
            val yearMonth = yearMonth(page)
            val monthState = rememberMonthState(yearMonth, state.startDayOfWeek, state.selectState)

            Month(
                state = monthState,
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

package io.github.taetae98coding.diary.compose.calendar.week

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.taetae98coding.diary.compose.calendar.CalendarDefaults
import io.github.taetae98coding.diary.compose.calendar.color.CalendarColors
import io.github.taetae98coding.diary.compose.calendar.item.CalendarTextItemUiState
import io.github.taetae98coding.diary.compose.calendar.item.CalendarWeatherUiState
import io.github.taetae98coding.diary.compose.calendar.modifier.drawDragRange
import kotlinx.datetime.LocalDate

@Composable
internal fun WeekOfMonth(
    state: WeekOfMonthState,
    modifier: Modifier = Modifier,
    primaryDateProvider: () -> List<LocalDate> = { emptyList() },
    colorTextProvider: () -> List<CalendarTextItemUiState.ColorText> = { emptyList() },
    weatherProvider: () -> List<CalendarWeatherUiState> = { emptyList() },
    holidayProvider: () -> List<CalendarTextItemUiState.Holiday> = { emptyList() },
    specialDayProvider: () -> List<CalendarTextItemUiState.SpecialDay> = { emptyList() },
    onColorTextClick: ((Any) -> Unit)? = null,
    colors: CalendarColors = CalendarDefaults.colors(),
) {
    Column(
        modifier = modifier.drawDragRange(
            state = state,
            colors = colors,
        ),
    ) {
        HorizontalDivider(thickness = 0.5.dp)
        Spacer(modifier = Modifier.height(2.dp))
        DayOfMonthRow(
            state = state,
            primaryDateProvider = primaryDateProvider,
            holidayProvider = holidayProvider,
            colors = colors,
        )
        CalendarItemGridColumn(
            state = state,
            colorTextProvider = colorTextProvider,
            weatherProvider = weatherProvider,
            holidayProvider = holidayProvider,
            specialDayProvider = specialDayProvider,
            onColorTextClick = onColorTextClick,
            modifier = Modifier.weight(1F),
            colors = colors,
        )
    }
}

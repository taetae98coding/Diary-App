package io.github.taetae98coding.diary.compose.calendar.day

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.taetae98coding.diary.compose.calendar.CalendarDefaults
import io.github.taetae98coding.diary.compose.calendar.color.CalendarColors
import io.github.taetae98coding.diary.compose.calendar.item.CalendarTextItemUiState
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.library.compose.color.wcagAAAContentColor
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

@Composable
internal fun DayOfMonth(
    state: DayOfMonthState,
    modifier: Modifier = Modifier,
    primaryDateProvider: () -> List<LocalDate> = { emptyList() },
    holidayProvider: () -> List<CalendarTextItemUiState.Holiday> = { emptyList() },
    colors: CalendarColors = CalendarDefaults.colors(),
) {
    val isPrimaryDate by remember(state) { derivedStateOf { state.localDate in primaryDateProvider() } }
    val isHoliday by remember(state) { derivedStateOf { holidayProvider().any { state.localDate in it.dateRange } } }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        PrimaryBox(
            isPrimaryDateProvider = { isPrimaryDate },
            colors = colors,
        ) {
            Text(
                text = state.localDate.day.toString(),
                color = if (isPrimaryDate) {
                    colors.primaryColor.wcagAAAContentColor()
                } else if (isHoliday) {
                    if (state.isInMonth) colors.sundayColor else colors.sundayVariantColor
                } else {
                    when (state.localDate.dayOfWeek) {
                        DayOfWeek.SATURDAY -> if (state.isInMonth) colors.saturdayColor else colors.saturdayVariantColor
                        DayOfWeek.SUNDAY -> if (state.isInMonth) colors.sundayColor else colors.sundayVariantColor
                        else -> if (state.isInMonth) colors.dayColor else colors.dayVariantColor
                    }
                },
                textAlign = TextAlign.Center,
                style = DiaryTheme.typography.labelMedium,
            )
        }
    }
}

@Composable
private fun PrimaryBox(
    isPrimaryDateProvider: () -> Boolean,
    modifier: Modifier = Modifier,
    colors: CalendarColors = CalendarDefaults.colors(),
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = if (isPrimaryDateProvider()) {
            Modifier.background(colors.primaryColor, CircleShape)
        } else {
            Modifier
        }.padding(6.dp),
    ) { measurable, constraints ->
        val placeableList = measurable.map { it.measure(constraints) }
        val size = maxOf(placeableList.maxOf { it.measuredWidth }, placeableList.maxOf { it.measuredHeight })

        layout(size, size) {
            placeableList.forEach {
                it.placeRelative(
                    (size - it.measuredWidth) / 2,
                    (size - it.measuredHeight) / 2,
                )
            }
        }
    }
}

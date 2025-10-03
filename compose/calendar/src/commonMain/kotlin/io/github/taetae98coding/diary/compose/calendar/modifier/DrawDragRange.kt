package io.github.taetae98coding.diary.compose.calendar.modifier

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import io.github.taetae98coding.diary.compose.calendar.color.CalendarColors
import io.github.taetae98coding.diary.compose.calendar.week.WeekOfMonthState
import io.github.taetae98coding.diary.library.kotlinx.datetime.dayNumber
import io.github.taetae98coding.diary.library.kotlinx.datetime.isOverlaps
import kotlinx.datetime.daysUntil

@Composable
internal fun Modifier.drawDragRange(
    state: WeekOfMonthState,
    colors: CalendarColors,
): Modifier {
    return drawBehind {
        val dateRange = state.selectState.range ?: return@drawBehind
        val drawDateRange = maxOf(dateRange.start, state.dateRange.start)..minOf(dateRange.endInclusive, state.dateRange.endInclusive)

        if (drawDateRange.isOverlaps(state.dateRange)) {
            drawRoundRect(
                color = colors.selectedColor,
                topLeft = Offset(size.width / 7F * dayNumber(drawDateRange.start.dayOfWeek, state.startDayOfWeek), 0F),
                size = size.copy(width = size.width / 7F * (drawDateRange.start.daysUntil(drawDateRange.endInclusive) + 1)),
            )
        }
    }
}

package io.github.taetae98coding.diary.compose.calendar

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import io.github.taetae98coding.diary.compose.calendar.color.CalendarColors
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.library.kotlinx.datetime.dayOfWeek
import kotlinx.datetime.DayOfWeek

@Composable
internal fun DayOfWeekRow(
    state: CalendarState,
    modifier: Modifier = Modifier,
    colors: CalendarColors = CalendarDefaults.colors(),
) {
    Row(modifier = modifier) {
        repeat(7) { dayNumber ->
            val dayOfWeek = dayOfWeek(dayNumber, state.startDayOfWeek)
            val text = when (dayOfWeek) {
                DayOfWeek.MONDAY -> "월"
                DayOfWeek.TUESDAY -> "화"
                DayOfWeek.WEDNESDAY -> "수"
                DayOfWeek.THURSDAY -> "목"
                DayOfWeek.FRIDAY -> "금"
                DayOfWeek.SATURDAY -> "토"
                DayOfWeek.SUNDAY -> "일"
            }

            val color = when (dayOfWeek) {
                DayOfWeek.SATURDAY -> colors.saturdayColor
                DayOfWeek.SUNDAY -> colors.sundayColor
                else -> colors.dayColor
            }

            Text(
                text = text,
                modifier = Modifier.weight(1f),
                color = color,
                textAlign = TextAlign.Center,
                style = DiaryTheme.typography.labelMediumEmphasized,
            )
        }
    }
}

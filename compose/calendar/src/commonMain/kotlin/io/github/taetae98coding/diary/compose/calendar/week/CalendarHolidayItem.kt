package io.github.taetae98coding.diary.compose.calendar.week

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.taetae98coding.diary.compose.calendar.CalendarDefaults
import io.github.taetae98coding.diary.compose.calendar.color.CalendarColors

@Composable
internal fun CalendarHolidayItem(
    item: CalendarWeekItem.Holiday,
    modifier: Modifier = Modifier,
    colors: CalendarColors = CalendarDefaults.colors(),
) {
    CalendarTextItem(
        text = item.text,
        color = colors.sundayColor,
        modifier = modifier,
    )
}

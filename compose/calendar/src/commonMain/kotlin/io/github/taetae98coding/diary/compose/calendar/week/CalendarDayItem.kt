package io.github.taetae98coding.diary.compose.calendar.week

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun CalendarDayItem(
    item: CalendarWeekItem.ColorText,
    modifier: Modifier = Modifier,
) {
    CalendarTextItem(
        text = item.text,
        color = item.color,
        modifier = modifier,
    )
}

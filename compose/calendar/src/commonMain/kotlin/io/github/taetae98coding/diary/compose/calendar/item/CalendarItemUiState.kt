package io.github.taetae98coding.diary.compose.calendar.item

import kotlinx.datetime.LocalDateRange

public sealed class CalendarItemUiState {

    internal abstract val dateRange: LocalDateRange
}

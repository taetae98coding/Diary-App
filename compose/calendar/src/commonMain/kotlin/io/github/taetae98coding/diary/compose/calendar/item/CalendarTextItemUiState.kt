package io.github.taetae98coding.diary.compose.calendar.item

import kotlinx.datetime.LocalDateRange

public sealed class CalendarTextItemUiState :
    CalendarItemUiState(),
    Comparable<CalendarTextItemUiState> {
    internal abstract val text: String

    public data class ColorText(
        val key: CalendarItemKey,
        override val text: String,
        override val dateRange: LocalDateRange,
        val color: Int,
    ) : CalendarTextItemUiState()

    public data class Holiday(
        override val text: String,
        val uri: String,
        override val dateRange: LocalDateRange,
    ) : CalendarTextItemUiState()

    public data class SpecialDay(
        override val text: String,
        val uri: String,
        override val dateRange: LocalDateRange,
    ) : CalendarTextItemUiState()

    override fun compareTo(other: CalendarTextItemUiState): Int {
        if (dateRange.start != other.dateRange.start) return compareValues(dateRange.start, other.dateRange.start)
        if (dateRange.endInclusive != other.dateRange.endInclusive) return -compareValues(dateRange.endInclusive, other.dateRange.endInclusive)

        return 0
    }
}

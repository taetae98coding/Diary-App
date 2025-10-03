package io.github.taetae98coding.diary.compose.calendar.item

import kotlin.time.Instant
import kotlinx.datetime.LocalDateRange
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

public data class CalendarWeatherUiState(
    val icon: List<CalendarWeatherIconUiState>,
    val temperature: Double,
    val instant: Instant,
) : CalendarItemUiState() {
    internal val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    override val dateRange: LocalDateRange = dateTime.date..dateTime.date
}

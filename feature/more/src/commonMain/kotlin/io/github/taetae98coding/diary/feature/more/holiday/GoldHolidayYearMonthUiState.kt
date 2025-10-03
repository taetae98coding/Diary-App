package io.github.taetae98coding.diary.feature.more.holiday

import io.github.taetae98coding.diary.compose.calendar.item.CalendarTextItemUiState
import kotlinx.datetime.LocalDateRange
import kotlinx.datetime.YearMonth

internal data class GoldHolidayYearMonthUiState(
    val yearMonth: YearMonth,
    val annualLeave: List<LocalDateRange>,
    val holiday: List<CalendarTextItemUiState.Holiday>,
    val goldHoliday: Int,
)

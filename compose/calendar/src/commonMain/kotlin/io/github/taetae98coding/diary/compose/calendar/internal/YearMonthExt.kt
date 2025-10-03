package io.github.taetae98coding.diary.compose.calendar.internal

import kotlinx.datetime.YearMonth
import kotlinx.datetime.number

internal fun yearMonth(page: Int): YearMonth {
    val year = page / 12 + 1
    val monthNumber = page % 12 + 1

    return YearMonth(year, monthNumber)
}

internal fun page(yearMonth: YearMonth): Int {
    return ((yearMonth.year - 1) * 12) + (yearMonth.month.number - 1)
}

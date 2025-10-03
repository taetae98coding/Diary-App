package io.github.taetae98coding.diary.library.kotlinx.datetime

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.isoDayNumber

public fun dayOfWeek(
    dayNumber: Int,
    startDayOfWeek: DayOfWeek,
): DayOfWeek {
    val days = startDayOfWeek.isoDayNumber + dayNumber

    return if (days > 7) {
        DayOfWeek(days - 7)
    } else {
        DayOfWeek(days)
    }
}

public fun dayNumber(
    dayOfWeek: DayOfWeek,
    startDayOfWeek: DayOfWeek,
): Int {
    val days = dayOfWeek.isoDayNumber - startDayOfWeek.isoDayNumber

    return if (days >= 0) {
        days
    } else {
        days + 7
    }
}

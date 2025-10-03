package io.github.taetae98coding.diary.library.kotlinx.datetime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

public fun LocalDate.toEpochMilliseconds(): Long {
    return atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
}

public fun localDate(epochMilliseconds: Long): LocalDate {
    return localDateTime(epochMilliseconds).date
}

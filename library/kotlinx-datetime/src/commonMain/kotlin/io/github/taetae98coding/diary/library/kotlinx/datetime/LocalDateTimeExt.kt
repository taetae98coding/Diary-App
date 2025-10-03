package io.github.taetae98coding.diary.library.kotlinx.datetime

import kotlin.time.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

public fun LocalDateTime.toEpochMilliseconds(): Long {
    return toInstant(TimeZone.UTC).toEpochMilliseconds()
}

public fun localDateTime(epochMilliseconds: Long): LocalDateTime {
    return Instant.fromEpochMilliseconds(epochMilliseconds).toLocalDateTime(TimeZone.UTC)
}

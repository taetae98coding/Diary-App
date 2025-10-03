package io.github.taetae98coding.diary.library.kotlinx.datetime

import kotlinx.datetime.LocalDateRange

public fun LocalDateRange.isOverlaps(other: LocalDateRange): Boolean {
    return start <= other.endInclusive && endInclusive >= other.start
}

package io.github.taetae98coding.diary.core.entity.holiday

import kotlinx.datetime.LocalDateRange

public data class SpecialDay(
    val name: String,
    val uri: String,
    val dateRange: LocalDateRange,
)

package io.github.taetae98coding.diary.core.entity.memo

import kotlin.uuid.Uuid
import kotlinx.datetime.LocalDateRange

public data class CalendarMemo(
    val id: Uuid,
    val title: String,
    val dateRange: LocalDateRange,
    val color: Int,
)

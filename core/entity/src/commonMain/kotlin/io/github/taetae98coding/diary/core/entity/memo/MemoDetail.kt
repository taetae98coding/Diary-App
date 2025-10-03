package io.github.taetae98coding.diary.core.entity.memo

import kotlinx.datetime.LocalDateRange

public data class MemoDetail(
    val title: String,
    val description: String,
    val dateRange: LocalDateRange?,
    val color: Int,
)

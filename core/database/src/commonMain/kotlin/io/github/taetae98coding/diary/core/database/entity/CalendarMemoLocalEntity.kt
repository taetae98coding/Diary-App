package io.github.taetae98coding.diary.core.database.entity

import androidx.room.ColumnInfo
import kotlin.uuid.Uuid
import kotlinx.datetime.LocalDate

public data class CalendarMemoLocalEntity(
    @ColumnInfo("id")
    val id: Uuid,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("start")
    val start: LocalDate,
    @ColumnInfo("endInclusive")
    val endInclusive: LocalDate,
    @ColumnInfo("calendarColor")
    val color: Int,
)

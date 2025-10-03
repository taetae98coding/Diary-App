package io.github.taetae98coding.diary.core.database.entity

import androidx.room.ColumnInfo
import kotlinx.datetime.LocalDate

public data class MemoDetailLocalEntity(
    @ColumnInfo(defaultValue = "")
    val title: String,
    @ColumnInfo(defaultValue = "")
    val description: String,
    @ColumnInfo(defaultValue = "null")
    val start: LocalDate?,
    @ColumnInfo(defaultValue = "null")
    val endInclusive: LocalDate? = null,
    @ColumnInfo(defaultValue = "0")
    val color: Int,
)

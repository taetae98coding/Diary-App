package io.github.taetae98coding.diary.core.holiday.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.datetime.LocalDate

@Entity(
    tableName = "Holiday",
    primaryKeys = ["name", "localDate"],
)
public data class HolidayLocalEntity(
    @ColumnInfo(defaultValue = "")
    val name: String,
    @ColumnInfo(defaultValue = "1998-01-09")
    val localDate: LocalDate,
)

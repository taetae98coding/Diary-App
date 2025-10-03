package io.github.taetae98coding.diary.core.holiday.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.datetime.LocalDate

@Entity(
    tableName = "SpecialDay",
    primaryKeys = ["name", "localDate"],
)
public data class SpecialDayLocalEntity(
    @ColumnInfo(defaultValue = "")
    val name: String,
    @ColumnInfo(defaultValue = "1998-01-09")
    val localDate: LocalDate,
)

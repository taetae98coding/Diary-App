package io.github.taetae98coding.diary.library.room.common.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

public data object LocalDateConverter {
    @TypeConverter
    public fun localDateToString(date: LocalDate): String {
        return date.toString()
    }

    @TypeConverter
    public fun stringToLocalDate(date: String): LocalDate {
        return LocalDate.parse(date)
    }
}

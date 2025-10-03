package io.github.taetae98coding.diary.library.room.common.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime

public data object LocalDateTimeConverter {
    @TypeConverter
    public fun localDateTimeToString(dateTime: LocalDateTime): String {
        return dateTime.toString()
    }

    @TypeConverter
    public fun stringToLocalDateTime(dateTime: String): LocalDateTime {
        return LocalDateTime.parse(dateTime)
    }
}

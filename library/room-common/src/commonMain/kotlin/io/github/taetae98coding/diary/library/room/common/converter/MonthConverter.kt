package io.github.taetae98coding.diary.library.room.common.converter

import androidx.room.TypeConverter
import kotlinx.datetime.Month
import kotlinx.datetime.number

public data object MonthConverter {
    @TypeConverter
    public fun monthToInt(month: Month): Int {
        return month.number
    }

    @TypeConverter
    public fun intToMonth(month: Int): Month {
        return Month(month)
    }
}

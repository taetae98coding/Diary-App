package io.github.taetae98coding.diary.core.holiday.database

import androidx.room.RoomDatabaseConstructor

internal expect object HolidayDatabaseConstructor : RoomDatabaseConstructor<HolidayDatabase> {
    override fun initialize(): HolidayDatabase
}

package io.github.taetae98coding.diary.core.database

import androidx.room.RoomDatabaseConstructor

internal expect object DiaryDatabaseConstructor : RoomDatabaseConstructor<DiaryDatabase> {
    override fun initialize(): DiaryDatabase
}

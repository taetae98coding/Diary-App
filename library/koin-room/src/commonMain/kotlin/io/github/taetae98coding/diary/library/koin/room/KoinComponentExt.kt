package io.github.taetae98coding.diary.library.koin.room

import androidx.room.RoomDatabase
import org.koin.core.component.KoinComponent

public expect inline fun <reified T : RoomDatabase> KoinComponent.roomDatabaseBuilder(
    name: String,
): RoomDatabase.Builder<T>

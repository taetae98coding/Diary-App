package io.github.taetae98coding.diary.library.koin.room

import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.taetae98coding.diary.library.kotlinx.file.documentDirectory
import org.koin.core.component.KoinComponent
import platform.Foundation.NSFileManager

public actual inline fun <reified T : RoomDatabase> KoinComponent.roomDatabaseBuilder(
    name: String,
): RoomDatabase.Builder<T> {
    return Room.databaseBuilder("${requireNotNull(NSFileManager.documentDirectory())}/$name")
}

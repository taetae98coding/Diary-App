package io.github.taetae98coding.diary.library.koin.room

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File
import org.koin.core.component.KoinComponent

public actual inline fun <reified T : RoomDatabase> KoinComponent.roomDatabaseBuilder(
    name: String,
): RoomDatabase.Builder<T> {
    val file = File("${storePath()}/$name")

    runCatching { file.parentFile?.mkdirs() }
    return Room.databaseBuilder(name = file.absolutePath)
}

public fun storePath(): String {
    return KoinComponentRoom.storePath()
}

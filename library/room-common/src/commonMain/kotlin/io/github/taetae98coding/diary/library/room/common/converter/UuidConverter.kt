package io.github.taetae98coding.diary.library.room.common.converter

import androidx.room.TypeConverter
import kotlin.uuid.Uuid

public data object UuidConverter {
    @TypeConverter
    public fun uuidToString(uuid: Uuid): String {
        return uuid.toString()
    }

    @TypeConverter
    public fun stringToUuid(uuid: String): Uuid {
        return Uuid.parse(uuid)
    }
}

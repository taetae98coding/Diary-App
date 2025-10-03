package io.github.taetae98coding.diary.core.mapper

import io.githbu.taetae98coding.diary.core.holiday.service.entity.HolidayRemoteEntity
import io.github.taetae98coding.diary.core.entity.holiday.Holiday
import io.github.taetae98coding.diary.core.holiday.database.entity.HolidayLocalEntity

public fun HolidayLocalEntity.toEntity(): Holiday {
    val prettyName = when (name) {
        "1월1일" -> "새해"
        "기독탄신일" -> "크리스마스"
        else -> name
    }

    return Holiday(
        name = prettyName,
        uri = "https://namu.wiki/w/$prettyName",
        dateRange = localDate..localDate,
    )
}

public fun HolidayRemoteEntity.toLocal(): HolidayLocalEntity {
    return HolidayLocalEntity(
        name = name,
        localDate = localDate,
    )
}

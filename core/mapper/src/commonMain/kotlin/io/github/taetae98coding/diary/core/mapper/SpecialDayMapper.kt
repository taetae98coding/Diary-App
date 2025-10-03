package io.github.taetae98coding.diary.core.mapper

import io.githbu.taetae98coding.diary.core.holiday.service.entity.HolidayRemoteEntity
import io.github.taetae98coding.diary.core.entity.holiday.SpecialDay
import io.github.taetae98coding.diary.core.holiday.database.entity.SpecialDayLocalEntity

public fun SpecialDayLocalEntity.toEntity(): SpecialDay {
    return SpecialDay(
        name = name,
        uri = "https://namu.wiki/w/$name",
        dateRange = localDate..localDate,
    )
}

public fun HolidayRemoteEntity.toLocalSpecialDayEntity(): SpecialDayLocalEntity {
    return SpecialDayLocalEntity(
        name = name,
        localDate = localDate,
    )
}

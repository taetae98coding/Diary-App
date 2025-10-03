package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.database.entity.CalendarMemoLocalEntity
import io.github.taetae98coding.diary.core.entity.memo.CalendarMemo

public fun CalendarMemoLocalEntity.toEntity(): CalendarMemo {
    return CalendarMemo(
        id = id,
        title = title,
        dateRange = start..endInclusive,
        color = color,
    )
}

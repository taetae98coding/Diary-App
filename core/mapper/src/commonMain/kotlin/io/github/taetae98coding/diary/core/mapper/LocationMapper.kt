package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.entity.location.Location
import io.github.taetae98coding.diary.core.ip.service.entity.LocationRemoteEntity

public fun LocationRemoteEntity.toEntity(): Location {
    return Location(
        latitude = latitude,
        longitude = longitude,
    )
}

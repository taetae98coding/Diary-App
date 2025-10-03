package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.database.entity.TagDetailLocalEntity
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.core.service.entity.tag.TagDetailRemoteEntity

public fun TagDetail.toLocal(): TagDetailLocalEntity {
    return TagDetailLocalEntity(
        title = title,
        description = description,
        color = color,
    )
}

public fun TagDetail.toRemote(): TagDetailRemoteEntity {
    return TagDetailRemoteEntity(
        title = title,
        description = description,
        color = color,
    )
}

public fun TagDetailLocalEntity.toEntity(): TagDetail {
    return TagDetail(
        title = title,
        description = description,
        color = color,
    )
}

public fun TagDetailLocalEntity.toRemote(): TagDetailRemoteEntity {
    return TagDetailRemoteEntity(
        title = title,
        description = description,
        color = color,
    )
}

public fun TagDetailRemoteEntity.toLocal(): TagDetailLocalEntity {
    return TagDetailLocalEntity(
        title = title,
        description = description,
        color = color,
    )
}

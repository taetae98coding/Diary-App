package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.database.entity.MemoDetailLocalEntity
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.core.service.entity.memo.MemoDetailRemoteEntity

public fun MemoDetail.toLocal(): MemoDetailLocalEntity {
    return MemoDetailLocalEntity(
        title = title,
        description = description,
        start = dateRange?.start,
        endInclusive = dateRange?.endInclusive,
        color = color,
    )
}

public fun MemoDetail.toRemote(): MemoDetailRemoteEntity {
    return MemoDetailRemoteEntity(
        title = title,
        description = description,
        start = dateRange?.start,
        endInclusive = dateRange?.endInclusive,
        color = color,
    )
}

public fun MemoDetailLocalEntity.toEntity(): MemoDetail {
    val start = start
    val endInclusive = endInclusive

    return MemoDetail(
        title = title,
        description = description,
        dateRange = if (start != null && endInclusive != null) {
            start..endInclusive
        } else {
            null
        },
        color = color,
    )
}

public fun MemoDetailLocalEntity.toRemote(): MemoDetailRemoteEntity {
    return MemoDetailRemoteEntity(
        title = title,
        description = description,
        start = start,
        endInclusive = endInclusive,
        color = color,
    )
}

public fun MemoDetailRemoteEntity.toLocal(): MemoDetailLocalEntity {
    return MemoDetailLocalEntity(
        title = title,
        description = description,
        start = start,
        endInclusive = endInclusive,
        color = color,
    )
}

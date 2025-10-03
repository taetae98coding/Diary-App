package io.github.taetae98coding.diary.core.entity.tag

import kotlin.uuid.Uuid

public data class Tag(
    val id: Uuid,
    val detail: TagDetail,
    val isFinished: Boolean,
    val isDeleted: Boolean,
    val updatedAt: Long,
    val createdAt: Long,
)

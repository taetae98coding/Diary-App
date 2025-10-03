package io.github.taetae98coding.diary.core.entity.buddy

import kotlin.uuid.Uuid

public data class Buddy(
    val id: Uuid,
    val email: String,
    val profileImage: String?,
)

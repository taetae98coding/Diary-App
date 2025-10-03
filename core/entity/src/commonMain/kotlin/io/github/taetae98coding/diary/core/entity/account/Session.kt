package io.github.taetae98coding.diary.core.entity.account

import kotlin.uuid.Uuid

public data class Session(
    val token: String,
    val id: Uuid,
    val email: String,
    val profileImage: String?,
)

package io.github.taetae98coding.diary.core.preferences.entity

import kotlin.uuid.Uuid

public data class SessionPreferencesEntity(
    val token: String,
    val id: Uuid,
    val email: String,
    val profileImage: String?,
)

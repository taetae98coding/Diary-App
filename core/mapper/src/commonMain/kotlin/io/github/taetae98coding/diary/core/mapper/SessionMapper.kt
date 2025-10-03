package io.github.taetae98coding.diary.core.mapper

import io.github.taetae98coding.diary.core.entity.account.Session
import io.github.taetae98coding.diary.core.preferences.entity.SessionPreferencesEntity
import io.github.taetae98coding.diary.core.service.entity.account.SessionRemoteEntity

public fun SessionPreferencesEntity.toEntity(): Session {
    return Session(
        token = token,
        id = id,
        email = email,
        profileImage = profileImage,
    )
}

public fun SessionRemoteEntity.toPreferences(): SessionPreferencesEntity {
    return SessionPreferencesEntity(
        token = token,
        id = account.id,
        email = account.email,
        profileImage = account.profileImage,
    )
}

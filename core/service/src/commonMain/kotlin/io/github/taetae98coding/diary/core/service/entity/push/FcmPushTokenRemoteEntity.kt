package io.github.taetae98coding.diary.core.service.entity.push

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class FcmPushTokenRemoteEntity(
    @SerialName("token")
    val token: String,
)

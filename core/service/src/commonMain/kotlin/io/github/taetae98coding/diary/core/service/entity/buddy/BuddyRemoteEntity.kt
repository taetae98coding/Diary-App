package io.github.taetae98coding.diary.core.service.entity.buddy

import kotlin.uuid.Uuid
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class BuddyRemoteEntity(
    @SerialName("id")
    val id: Uuid,
    @SerialName("email")
    val email: String,
    @SerialName("profileImage")
    val profileImage: String?,
)

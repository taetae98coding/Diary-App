package io.github.taetae98coding.diary.core.service.entity.buddy

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class BuddyGroupDetailRemoteEntity(
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
)

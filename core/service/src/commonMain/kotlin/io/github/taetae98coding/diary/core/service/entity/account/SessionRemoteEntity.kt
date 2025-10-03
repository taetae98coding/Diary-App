package io.github.taetae98coding.diary.core.service.entity.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class SessionRemoteEntity(
    @SerialName("token")
    val token: String,
    @SerialName("account")
    val account: AccountRemoteEntity,
)

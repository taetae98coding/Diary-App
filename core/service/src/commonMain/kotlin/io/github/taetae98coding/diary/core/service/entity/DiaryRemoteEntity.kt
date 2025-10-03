package io.github.taetae98coding.diary.core.service.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class DiaryRemoteEntity<T>(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("body")
    val body: T?,
) {
    public fun requireSuccess(): DiaryRemoteEntity<T> {
        check(code == SUCCESS)
        return this
    }

    public fun requireBody(): T {
        return requireNotNull(body)
    }

    public companion object {
        public const val SUCCESS: Int = 200
        public const val NOT_FOUND: Int = 404
    }
}

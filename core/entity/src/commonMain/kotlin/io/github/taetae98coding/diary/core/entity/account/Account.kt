package io.github.taetae98coding.diary.core.entity.account

import kotlin.uuid.Uuid

public sealed class Account {
    public abstract val token: String?
    public abstract val id: Uuid

    public data object Guest : Account() {
        override val token: String? = null
        override val id: Uuid = Uuid.NIL
    }

    public data class User(
        override val token: String,
        override val id: Uuid,
        val email: String,
        val profileImage: String?,
    ) : Account()
}

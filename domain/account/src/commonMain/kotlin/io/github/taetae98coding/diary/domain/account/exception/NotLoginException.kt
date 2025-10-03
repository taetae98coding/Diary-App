package io.github.taetae98coding.diary.domain.account.exception

public class NotLoginException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : IllegalStateException(message, cause)

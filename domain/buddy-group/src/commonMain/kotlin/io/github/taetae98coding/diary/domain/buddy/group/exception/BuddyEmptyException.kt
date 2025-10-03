package io.github.taetae98coding.diary.domain.buddy.group.exception

public class BuddyEmptyException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : IllegalStateException(message, cause)

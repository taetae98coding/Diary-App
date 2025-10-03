package io.github.taetae98coding.diary.domain.buddy.group.exception

public class BuddyGroupTitleBlankException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : IllegalStateException(message, cause)

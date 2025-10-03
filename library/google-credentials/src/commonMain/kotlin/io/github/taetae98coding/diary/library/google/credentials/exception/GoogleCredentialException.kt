package io.github.taetae98coding.diary.library.google.credentials.exception

public open class GoogleCredentialException(
    override val cause: Throwable? = null,
) : Exception(
    cause,
)

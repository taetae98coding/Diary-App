package io.github.taetae98coding.diary.library.google.credentials.exception

public class GoogleCredentialNotExistException(
    override val cause: Throwable? = null,
) : GoogleCredentialException(
    cause = cause,
)

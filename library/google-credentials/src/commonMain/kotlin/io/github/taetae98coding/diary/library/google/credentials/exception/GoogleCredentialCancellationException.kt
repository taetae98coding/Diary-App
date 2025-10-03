package io.github.taetae98coding.diary.library.google.credentials.exception

public class GoogleCredentialCancellationException(
    override val cause: Throwable? = null,
) : GoogleCredentialException(
    cause = cause,
)

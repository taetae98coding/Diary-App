package io.github.taetae98coding.diary.domain.credentials.repository

public interface CredentialsRepository {
    public suspend fun updateGoogleSession(idToken: String)
    public suspend fun delete()
}

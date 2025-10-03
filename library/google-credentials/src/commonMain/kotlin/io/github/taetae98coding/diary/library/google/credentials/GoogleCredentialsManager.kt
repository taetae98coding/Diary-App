package io.github.taetae98coding.diary.library.google.credentials

public interface GoogleCredentialsManager {
    public suspend fun getIdToken(): String
}

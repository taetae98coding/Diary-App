package io.github.taetae98coding.diary.library.google.credentials.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.taetae98coding.diary.library.google.credentials.GoogleCredentialManagerImpl
import io.github.taetae98coding.diary.library.google.credentials.GoogleCredentialsManager

@Composable
public actual fun rememberGoogleCredentialsManager(): GoogleCredentialsManager {
    return remember {
        GoogleCredentialManagerImpl()
    }
}

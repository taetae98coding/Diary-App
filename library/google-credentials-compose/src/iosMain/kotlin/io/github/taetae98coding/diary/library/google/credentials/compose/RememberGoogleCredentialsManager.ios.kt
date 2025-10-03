package io.github.taetae98coding.diary.library.google.credentials.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.uikit.LocalUIViewController
import io.github.taetae98coding.diary.library.google.credentials.GoogleCredentialsManager
import io.github.taetae98coding.diary.library.google.credentials.GoogleCredentialsManagerImpl

@Composable
public actual fun rememberGoogleCredentialsManager(): GoogleCredentialsManager {
    val uiViewController = LocalUIViewController.current

    return remember(uiViewController) { GoogleCredentialsManagerImpl(uiViewController) }
}

package io.github.taetae98coding.diary.compose.core.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.expressiveLightColorScheme
import androidx.compose.runtime.Composable

@Composable
internal actual fun diaryLightColorScheme(): ColorScheme {
    return expressiveLightColorScheme()
}

@Composable
internal actual fun diaryDarkColorScheme(): ColorScheme {
    return darkColorScheme()
}

package io.github.taetae98coding.diary.compose.core.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
internal actual fun diaryLightColorScheme(): ColorScheme {
    return dynamicLightColorScheme(LocalContext.current)
}

@Composable
internal actual fun diaryDarkColorScheme(): ColorScheme {
    return dynamicDarkColorScheme(LocalContext.current)
}

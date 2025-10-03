package io.github.taetae98coding.diary.compose.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp

public data object DiaryTheme {
    public val colorScheme: ColorScheme
        @Composable
        get() = MaterialTheme.colorScheme

    public val typography: Typography
        @Composable
        get() = MaterialTheme.typography

    public val dimens: DiaryDimens
        @Composable
        get() = LocalDiaryDimens.current
}

@Composable
public fun DiaryTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalDiaryDimens provides DiaryDimens(
            screenHorizontalPadding = 12.dp,
            screenVerticalPadding = 8.dp,
            diaryHorizontalPadding = 16.dp,
            diaryVerticalPadding = 8.dp,
            itemSpace = 4.dp,
        ),
    ) {
        val colorScheme = if (isSystemInDarkTheme()) {
            diaryDarkColorScheme()
        } else {
            diaryLightColorScheme()
        }

        MaterialExpressiveTheme(
            colorScheme = colorScheme,
            content = content,
        )
    }
}

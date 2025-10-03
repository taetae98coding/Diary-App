package io.github.taetae98coding.diary.compose.core.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

public data class DiaryDimens(
    val screenHorizontalPadding: Dp,
    val screenVerticalPadding: Dp,
    val diaryHorizontalPadding: Dp,
    val diaryVerticalPadding: Dp,
    val itemSpace: Dp,
) {
    val screenPaddingValues: PaddingValues
        get() = PaddingValues(screenHorizontalPadding, screenVerticalPadding)

    val diaryPaddingValues: PaddingValues
        get() = PaddingValues(diaryHorizontalPadding, diaryVerticalPadding)
}

internal val LocalDiaryDimens = staticCompositionLocalOf<DiaryDimens> { error("DiaryDimen Not Provide") }

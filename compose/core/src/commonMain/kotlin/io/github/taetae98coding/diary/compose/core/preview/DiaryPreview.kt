package io.github.taetae98coding.diary.compose.core.preview

import androidx.compose.ui.tooling.preview.Preview

@Preview
@Target(
    allowedTargets = [
        AnnotationTarget.FUNCTION,
    ],
)
internal annotation class DiaryPreview

package io.github.taetae98coding.diary.compose.core.preview

import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Target(
    allowedTargets = [
        AnnotationTarget.FUNCTION,
    ],
)
internal annotation class DiaryPreview

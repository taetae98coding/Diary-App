package io.github.taetae98coding.diary.compose.core.icon

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.taetae98coding.diary.compose.core.Res
import io.github.taetae98coding.diary.compose.core.ic_apple
import io.github.taetae98coding.diary.compose.core.preview.DiaryPreview
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import org.jetbrains.compose.resources.imageResource

@Composable
public fun AppleIcon(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Image(
        bitmap = imageResource(Res.drawable.ic_apple),
        contentDescription = contentDescription,
        modifier = modifier,
    )
}

@DiaryPreview
@Composable
private fun Preview() {
    DiaryTheme {
        AppleIcon()
    }
}

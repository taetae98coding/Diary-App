package io.github.taetae98coding.diary.compose.core.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.taetae98coding.diary.compose.core.preview.DiaryPreview
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme

public val CalendarImageVector: ImageVector by lazy { Icons.Rounded.CalendarMonth }

@Composable
public fun CalendarIcon(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current,
) {
    Icon(
        imageVector = CalendarImageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint,
    )
}

@DiaryPreview
@Composable
private fun Preview() {
    DiaryTheme {
        CalendarIcon()
    }
}

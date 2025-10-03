package io.github.taetae98coding.diary.compose.core.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.taetae98coding.diary.compose.core.preview.DiaryPreview
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme

@Composable
public fun LogoutIcon(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current,
) {
    Icon(
        imageVector = Icons.AutoMirrored.Rounded.Logout,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint,
    )
}

@DiaryPreview
@Composable
private fun Preview() {
    DiaryTheme {
        LogoutIcon()
    }
}

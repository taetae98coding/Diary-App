package io.github.taetae98coding.diary.compose.core.image

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import io.github.taetae98coding.diary.compose.core.icon.AccountIcon

@Composable
public fun ProfileImage(
    model: Any?,
    modifier: Modifier = Modifier,
    contentDescription: String? = "프로필",
    shape: Shape = CircleShape,
) {
    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    val backgroundColor = if (isSystemInDarkTheme()) {
        Color.Black
    } else {
        Color.White
    }

    SubcomposeAsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier.clip(shape).background(backgroundColor),
        loading = {
            AccountIcon(
                modifier = Modifier.padding(6.dp),
                tint = contentColor,
            )
        },
        error = {
            AccountIcon(
                modifier = Modifier.padding(6.dp),
                tint = contentColor,
            )
        },
        contentScale = ContentScale.Crop,
    )
}

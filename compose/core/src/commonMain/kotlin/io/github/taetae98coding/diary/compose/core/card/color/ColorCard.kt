package io.github.taetae98coding.diary.compose.core.card.color

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import io.github.taetae98coding.diary.library.compose.color.rgbText
import io.github.taetae98coding.diary.library.compose.color.wcagAAAContentColor

@Composable
public fun ColorCard(
    state: ColorCardState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.clip(CardDefaults.shape)
            .drawBehind { drawRect(state.color) }
            .clickable { state.showColorPicker() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "#${state.color.rgbText()}",
            modifier = Modifier.padding(36.dp),
            color = state.color.wcagAAAContentColor(),
        )
    }
}

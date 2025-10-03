package io.github.taetae98coding.diary.compose.core.color

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.taetae98coding.diary.compose.core.icon.RefreshIcon
import io.github.taetae98coding.diary.library.compose.color.rgbText
import io.github.taetae98coding.diary.library.compose.color.wcagAAAContentColor
import kotlinx.coroutines.launch

@Composable
public fun ColorPicker(
    state: ColorPickerState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        ColorPreviewBox(
            state = state,
            modifier = Modifier.fillMaxWidth(),
        )
        Slider(
            state = state.redSliderState,
            modifier = Modifier.padding(horizontal = 8.dp),
            colors = SliderDefaults.colors(
                thumbColor = if (isSystemInDarkTheme()) Color(0xFFFF8A80) else Color(0xFFD32F2F),
                activeTrackColor = Color(0xFFFFCDD2),
                activeTickColor = Color(0xFFE57373),
            ),
        )
        Slider(
            state = state.greenSliderState,
            modifier = Modifier.padding(horizontal = 8.dp),
            colors = SliderDefaults.colors(
                thumbColor = if (isSystemInDarkTheme()) Color(0xFF80E27E) else Color(0xFF388E3C),
                activeTrackColor = Color(0xFFC8E6C9),
                activeTickColor = Color(0xFF81C784),
            ),
        )
        Slider(
            state = state.blueSliderState,
            modifier = Modifier.padding(horizontal = 8.dp),
            colors = SliderDefaults.colors(
                thumbColor = if (isSystemInDarkTheme()) Color(0xFF82B1FF) else Color(0xFF1976D2),
                activeTrackColor = Color(0xFFBBDEFB),
                activeTickColor = Color(0xFF64B5F6),
            ),
        )
    }
}

@Composable
private fun ColorPreviewBox(
    state: ColorPickerState,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier.defaultMinSize(minHeight = 200.dp)
            .drawBehind { drawRect(state.color) },
    ) {
        Text(
            text = "#${state.color.rgbText()}",
            modifier = Modifier.align(Alignment.Center),
            color = state.color.wcagAAAContentColor(),
        )

        IconButton(
            onClick = { coroutineScope.launch { state.animateRandomColor() } },
            modifier = Modifier.align(Alignment.TopEnd)
                .padding(8.dp),
            colors = IconButtonDefaults.iconButtonColors(contentColor = state.color.wcagAAAContentColor()),
        ) {
            RefreshIcon()
        }
    }
}

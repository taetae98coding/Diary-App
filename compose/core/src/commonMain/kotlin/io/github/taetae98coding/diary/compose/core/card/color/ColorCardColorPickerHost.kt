package io.github.taetae98coding.diary.compose.core.card.color

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import io.github.taetae98coding.diary.compose.core.color.ColorPicker
import io.github.taetae98coding.diary.compose.core.color.ColorPickerDialog
import io.github.taetae98coding.diary.compose.core.color.rememberColorPickerState
import kotlinx.coroutines.launch

@Composable
public fun ColorCardColorPickerHost(
    state: ColorCardState,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    if (state.isColorPickerVisible) {
        val colorPickerState = rememberColorPickerState(
            initialColor = state.color,
        )

        ColorPickerDialog(
            onDismissRequest = { state.hideColorPicker() },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch { state.animateUpdateColor(colorPickerState.color) }
                        state.hideColorPicker()
                    },
                ) {
                    Text(text = "확인")
                }
            },
        ) {
            ColorPicker(state = colorPickerState)
        }
    }
}

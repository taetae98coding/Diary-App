package io.github.taetae98coding.diary.compose.core.card.color

import androidx.compose.animation.Animatable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import io.github.taetae98coding.diary.library.compose.color.randomColor

public class ColorCardState internal constructor(
    initialColor: Color,
) {
    internal var isColorPickerVisible by mutableStateOf(false)
        private set

    public var color: Color by mutableStateOf(initialColor)
        private set

    internal fun showColorPicker() {
        isColorPickerVisible = true
    }

    internal fun hideColorPicker() {
        isColorPickerVisible = false
    }

    public suspend fun animateUpdateColor(value: Color) {
        Animatable(color).animateTo(value) {
            color = this.value
        }
    }

    public companion object {
        internal fun saver(): Saver<ColorCardState, Any> {
            return listSaver(
                save = {
                    listOf(
                        it.isColorPickerVisible,
                        it.color.toArgb(),
                    )
                },
                restore = {
                    ColorCardState(initialColor = Color(it[1] as Int)).apply {
                        isColorPickerVisible = it[0] as Boolean
                    }
                },
            )
        }
    }
}

@Composable
public fun rememberColorCardState(
    vararg inputs: Any?,
    initialColor: Color = randomColor(),
): ColorCardState {
    return rememberSaveable(
        inputs = inputs,
        saver = ColorCardState.saver(),
    ) {
        ColorCardState(initialColor = initialColor)
    }
}

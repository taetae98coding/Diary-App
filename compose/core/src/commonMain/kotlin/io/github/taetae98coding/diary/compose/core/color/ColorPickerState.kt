package io.github.taetae98coding.diary.compose.core.color

import androidx.compose.animation.Animatable
import androidx.compose.material3.SliderState
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import io.github.taetae98coding.diary.library.compose.color.randomColor

public class ColorPickerState internal constructor(
    internal val redSliderState: SliderState,
    internal val greenSliderState: SliderState,
    internal val blueSliderState: SliderState,
) {
    public val color: Color by derivedStateOf { Color(redSliderState.value, greenSliderState.value, blueSliderState.value) }

    internal suspend fun animateRandomColor() {
        Animatable(color).animateTo(randomColor()) {
            redSliderState.value = value.red
            greenSliderState.value = value.green
            blueSliderState.value = value.blue
        }
    }
}

@Composable
public fun rememberColorPickerState(
    initialColor: Color = randomColor(),
): ColorPickerState {
    val redSliderState = rememberSliderState(value = initialColor.red)
    val greenSliderState = rememberSliderState(value = initialColor.green)
    val blueSliderState = rememberSliderState(value = initialColor.blue)

    return remember(
        redSliderState,
        greenSliderState,
        blueSliderState,
    ) {
        ColorPickerState(
            redSliderState = redSliderState,
            greenSliderState = greenSliderState,
            blueSliderState = blueSliderState,
        )
    }
}

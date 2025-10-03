package io.github.taetae98coding.diary.library.compose.color

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.random.nextInt

public fun Color.rgbText(): String {
    return "${channelToHex(red)}${channelToHex(green)}${channelToHex(blue)}"
}

public fun Color.wcagAAAContentColor(
    palette: List<Color> = listOf(
        Color.White,
        Color.Black,
    ),
): Color {
    return palette.maxByOrNull { calculateContrastRatio(this, it) } ?: Color.White
}

public fun randomColor(): Color {
    return Color(0xFF000000 + Random.nextInt(0..0xFFFFFF))
}

private fun calculateContrastRatio(color1: Color, color2: Color): Double {
    val lum1 = color1.luminance()
    val lum2 = color2.luminance()

    val lighter = maxOf(lum1, lum2)
    val darker = minOf(lum1, lum2)

    return (lighter + 0.05) / (darker + 0.05)
}

private fun channelToHex(channel: Float): String {
    return (channel * 255).roundToInt()
        .toString(16)
        .padStart(2, '0')
        .uppercase()
}

package io.github.taetae98coding.diary.compose.calendar.week

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import kotlin.math.roundToInt

@Composable
internal fun CalendarWeatherItem(
    item: CalendarWeekItem.Weather,
    modifier: Modifier = Modifier,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            item.icon.forEach {
                AsyncImage(
                    model = it.icon,
                    contentDescription = it.description,
                    modifier = Modifier.weight(1F)
                        .size(32.dp),
                    contentScale = ContentScale.Fit,
                )
            }
        }
        Text(
            text = "${(item.temperature * 10F).roundToInt() / 10F}°",
            maxLines = 1,
            style = DiaryTheme.typography.labelSmall,
        )
    }
}

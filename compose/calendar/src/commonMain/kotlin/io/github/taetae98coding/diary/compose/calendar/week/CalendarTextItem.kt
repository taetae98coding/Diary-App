package io.github.taetae98coding.diary.compose.calendar.week

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.library.compose.color.wcagAAAContentColor

@Composable
internal fun CalendarTextItem(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier.background(color, RoundedCornerShape(4.dp))
            .basicMarquee(iterations = Int.MAX_VALUE)
            .padding(2.dp),
        color = color.wcagAAAContentColor(),
        textAlign = TextAlign.Center,
        maxLines = 1,
        style = DiaryTheme.typography.labelMedium,
    )
}

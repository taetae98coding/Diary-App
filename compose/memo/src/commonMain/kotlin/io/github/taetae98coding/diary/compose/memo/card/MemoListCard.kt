package io.github.taetae98coding.diary.compose.memo.card

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.core.entity.memo.Memo
import kotlinx.datetime.number

@Composable
public fun MemoListCard(
    onClick: () -> Unit,
    uiState: Memo?,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)) {
            val dateRange = uiState?.detail?.dateRange

            Text(
                text = uiState?.detail?.title.orEmpty(),
                modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
                maxLines = 1,
                style = DiaryTheme.typography.titleLargeEmphasized,
            )
            if (dateRange != null) {
                val text = listOfNotNull(dateRange.start, dateRange.endInclusive)
                    .distinct()
                    .joinToString(" ~ ") { "${it.year}년 ${it.month.number}월 ${it.day}일" }

                Text(
                    text = text,
                    modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
                    maxLines = 1,
                    style = DiaryTheme.typography.labelMedium,
                )
            }
        }
    }
}

package io.github.taetae98coding.diary.presenter.tag.list

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.taetae98coding.diary.compose.core.chip.DiaryAssistChip
import io.github.taetae98coding.diary.compose.core.icon.MemoIcon
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.core.entity.tag.Tag

@Composable
internal fun TagListCard(
    onClick: () -> Unit,
    onMemo: () -> Unit,
    uiState: Tag?,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Title(uiState = uiState)
            ActionChipRow(onMemo = onMemo)
        }
    }
}

@Composable
private fun Title(
    uiState: Tag?,
    modifier: Modifier = Modifier,
) {
    Text(
        text = uiState?.detail?.title.orEmpty(),
        modifier = modifier.basicMarquee(iterations = Int.MAX_VALUE),
        maxLines = 1,
        style = DiaryTheme.typography.titleLargeEmphasized,
    )
}

@Composable
private fun ActionChipRow(
    onMemo: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        DiaryAssistChip(
            onClick = onMemo,
            label = { Text(text = "메모") },
            leadingIcon = { MemoIcon() },
            colors = AssistChipDefaults.assistChipColors()
                .copy(leadingIconContentColor = AssistChipDefaults.assistChipColors().labelColor),
        )
    }
}

package io.github.taetae98coding.diary.presenter.tag.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.core.entity.tag.Tag

@Composable
internal fun TagListCard(
    onClick: () -> Unit,
    uiState: Tag?,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)) {
            Text(
                text = uiState?.detail?.title.orEmpty(),
                style = DiaryTheme.typography.titleLargeEmphasized,
            )
        }
    }
}

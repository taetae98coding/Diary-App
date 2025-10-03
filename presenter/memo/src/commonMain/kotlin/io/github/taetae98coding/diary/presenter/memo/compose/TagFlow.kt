package io.github.taetae98coding.diary.presenter.memo.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import io.github.taetae98coding.diary.compose.core.chip.DiaryInputChip
import io.github.taetae98coding.diary.compose.core.icon.TagIcon
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.library.paging.compose.isEmptyVisible
import io.github.taetae98coding.diary.library.paging.compose.isError
import io.github.taetae98coding.diary.library.paging.compose.isLoadingVisible

@Composable
internal fun <T : Any> TagFlow(
    pagingItems: LazyPagingItems<T>,
    tag: @Composable (T?) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier.verticalScroll(state = rememberScrollState())
            .padding(DiaryTheme.dimens.itemSpace),
        horizontalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace, Alignment.CenterVertically),
        itemVerticalAlignment = Alignment.CenterVertically,
    ) {
        if (pagingItems.isLoadingVisible()) {
            ContainedLoadingIndicator()
        } else if (pagingItems.isError()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "네트워크 상태를 확인하세요")
                Button(onClick = pagingItems::retry) {
                    Text(text = "재시도")
                }
            }
        } else if (pagingItems.isEmptyVisible()) {
            Text(text = "태그가 없습니다")
        } else {
            repeat(pagingItems.itemCount) {
                val uiState = if (pagingItems.itemCount > it) {
                    pagingItems[it]
                } else {
                    null
                }

                tag(uiState)
            }
        }
    }
}

@Composable
internal fun TagChip(
    isSelected: Boolean,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: SelectableChipColors = InputChipDefaults.inputChipColors(),
) {
    DiaryInputChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(text = title) },
        modifier = modifier,
        leadingIcon = { TagIcon() },
        colors = colors,
    )
}

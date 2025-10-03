package io.github.taetae98coding.diary.feature.calendar.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.taetae98coding.diary.compose.core.chip.DiaryInputChip
import io.github.taetae98coding.diary.compose.core.icon.TagIcon
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.core.entity.tag.MemoTagFilter
import io.github.taetae98coding.diary.library.paging.compose.isEmptyVisible
import io.github.taetae98coding.diary.library.paging.compose.isLoadingVisible
import kotlin.uuid.Uuid
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CalendarMemoFilterDialog(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CalendarMemoTagFilterViewModel = koinViewModel(),
) {
    CalendarMemoFilterDialog(
        pagingItems = viewModel.pagingData.collectAsLazyPagingItems(),
        onDismissRequest = navigateUp,
        onAddMemoFilterTag = viewModel::add,
        onRemoveMemoFilterTag = viewModel::remove,
        modifier = modifier,
    )
}

@Composable
private fun CalendarMemoFilterDialog(
    pagingItems: LazyPagingItems<MemoTagFilter>,
    onDismissRequest: () -> Unit,
    onAddMemoFilterTag: (Uuid) -> Unit,
    onRemoveMemoFilterTag: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth()
                .height(250.dp)
                .verticalScroll(state = rememberScrollState())
                .padding(DiaryTheme.dimens.itemSpace),
            horizontalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace, Alignment.CenterVertically),
            itemVerticalAlignment = Alignment.CenterVertically,
        ) {
            if (pagingItems.isLoadingVisible()) {
                ContainedLoadingIndicator()
            } else if (pagingItems.isEmptyVisible()) {
                Text(text = "태그가 없습니다")
            } else {
                repeat(pagingItems.itemCount) {
                    val uiState = if (pagingItems.itemCount > it) {
                        pagingItems[it]
                    } else {
                        null
                    }

                    MemoTagFilterChip(
                        uiState = uiState,
                        onClick = {
                            if (uiState == null) return@MemoTagFilterChip

                            if (uiState.isFilter) {
                                onRemoveMemoFilterTag(uiState.tag.id)
                            } else {
                                onAddMemoFilterTag(uiState.tag.id)
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun MemoTagFilterChip(
    uiState: MemoTagFilter?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DiaryInputChip(
        selected = uiState?.isFilter ?: false,
        onClick = onClick,
        label = { Text(text = uiState?.tag?.detail?.title.orEmpty()) },
        leadingIcon = { TagIcon() },
    )
}

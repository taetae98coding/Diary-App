package io.github.taetae98coding.diary.presenter.memo.filter

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.taetae98coding.diary.compose.core.box.LoadingBox
import io.github.taetae98coding.diary.compose.core.chip.DiaryInputChip
import io.github.taetae98coding.diary.compose.core.icon.TagIcon
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.core.entity.tag.TagFilter
import io.github.taetae98coding.diary.library.paging.compose.isEmptyVisible
import io.github.taetae98coding.diary.library.paging.compose.isLoadingVisible
import kotlin.uuid.Uuid

private enum class MemoListFilterUiState {
    Loading,
    Empty,
    Content,
}

@Composable
public fun MemoListFilterDialog(
    stateHolder: MemoListFilterStateHolder,
    onDismissRequest: () -> Unit,
    onAddTag: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MemoListFilterDialog(
        pagingItems = stateHolder.pagingData.collectAsLazyPagingItems(),
        onDismissRequest = onDismissRequest,
        onAddTag = onAddTag,
        onAddTagFilter = stateHolder::addTagFilter,
        onRemoveTagFilter = stateHolder::removeTagFilter,
        modifier = modifier,
    )
}

@Composable
private fun MemoListFilterDialog(
    pagingItems: LazyPagingItems<TagFilter>,
    onDismissRequest: () -> Unit,
    onAddTag: () -> Unit,
    onAddTagFilter: (Uuid) -> Unit,
    onRemoveTagFilter: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState = when {
        pagingItems.isLoadingVisible() -> MemoListFilterUiState.Loading
        pagingItems.isEmptyVisible() -> MemoListFilterUiState.Empty
        else -> MemoListFilterUiState.Content
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    ) {
        AnimatedContent(
            targetState = uiState,
            modifier = Modifier.fillMaxWidth()
                .height(250.dp),
        ) { value ->
            when (value) {
                MemoListFilterUiState.Loading -> {
                    LoadingBox()
                }

                MemoListFilterUiState.Empty -> {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = "태그가 없습니다")
                        Button(onClick = onAddTag) {
                            Text(text = "태그 추가")
                        }
                    }
                }

                MemoListFilterUiState.Content -> {
                    FlowRow(
                        modifier = Modifier.verticalScroll(state = rememberScrollState())
                            .padding(DiaryTheme.dimens.itemSpace),
                        horizontalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace, Alignment.CenterHorizontally),
                        verticalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace, Alignment.CenterVertically),
                        itemVerticalAlignment = Alignment.CenterVertically,
                    ) {
                        repeat(pagingItems.itemCount) {
                            val uiState = if (pagingItems.itemCount > it) {
                                pagingItems[it]
                            } else {
                                null
                            }

                            TagFilterChip(
                                uiState = uiState,
                                onClick = {
                                    if (uiState == null) return@TagFilterChip

                                    if (uiState.isFilter) {
                                        onRemoveTagFilter(uiState.tag.id)
                                    } else {
                                        onAddTagFilter(uiState.tag.id)
                                    }
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TagFilterChip(
    uiState: TagFilter?,
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

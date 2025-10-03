package io.github.taetae98coding.diary.feature.memo.list

import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.compose.core.icon.TagIcon
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.feature.memo.MemoScrollState
import io.github.taetae98coding.diary.library.lifecycle.common.ifResumed
import io.github.taetae98coding.diary.presenter.memo.list.MemoListScaffold
import io.github.taetae98coding.diary.presenter.memo.list.MemoListScaffoldState
import io.github.taetae98coding.diary.presenter.memo.list.rememberMemoListScaffoldState
import kotlin.uuid.Uuid
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MemoListScreen(
    scrollState: MemoScrollState,
    navigateToMemoListFilter: () -> Unit,
    navigateToMemoAdd: () -> Unit,
    navigateToMemoDetail: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    listViewModel: MemoListViewModel = koinViewModel(),
    filterViewModel: MemoListFilterViewModel = koinViewModel(),
) {
    val state = rememberMemoListScaffoldState()
    val lifecycleOwner = LocalLifecycleOwner.current

    MemoListScaffold(
        listStateHolder = listViewModel,
        onAdd = dropUnlessResumed { navigateToMemoAdd() },
        onMemo = { lifecycleOwner.ifResumed { navigateToMemoDetail(it) } },
        modifier = modifier,
        state = state,
        title = { Text(text = "메모") },
        actions = {
            val hasFilter by filterViewModel.hasFilter.collectAsStateWithLifecycle()

            IconButton(
                onClick = dropUnlessResumed { navigateToMemoListFilter() },
                colors = if (hasFilter) {
                    IconButtonDefaults.iconButtonColors(contentColor = DiaryTheme.colorScheme.primary)
                } else {
                    IconButtonDefaults.iconButtonColors()
                },
            ) {
                TagIcon()
            }
        },
    )

    ScrollEffect(
        state = state,
        scrollState = scrollState,
    )
}

@Composable
private fun ScrollEffect(
    state: MemoListScaffoldState,
    scrollState: MemoScrollState,
) {
    LaunchedEffect(
        state.lazyListState,
        scrollState.hasToScrollTop,
    ) {
        if (scrollState.hasToScrollTop) {
            state.lazyListState.animateScrollToItem(0)
            scrollState.onScrollTop()
        }
    }
}

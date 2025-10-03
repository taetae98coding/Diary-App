package io.github.taetae98coding.diary.presenter.memo.list

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SwipeToDismissBoxDefaults
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import io.github.taetae98coding.diary.compose.core.box.FinishAndDeleteBox
import io.github.taetae98coding.diary.compose.core.button.FloatingAddButton
import io.github.taetae98coding.diary.compose.core.column.PullToRefreshLazyColumn
import io.github.taetae98coding.diary.compose.core.effect.PagingErrorMessageEffect
import io.github.taetae98coding.diary.compose.core.effect.PagingReloadEffect
import io.github.taetae98coding.diary.compose.core.snackbar.showSnackbarImmediate
import io.github.taetae98coding.diary.compose.core.snackbar.showSnackbarImmediateWithActions
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.library.compose.foundation.shortcutFocus
import io.github.taetae98coding.diary.library.compose.ui.onAddShortcut
import kotlin.uuid.Uuid
import kotlinx.coroutines.launch

@Composable
public fun MemoListScaffold(
    listStateHolder: MemoListStateHolder,
    onAdd: () -> Unit,
    onMemo: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    state: MemoListScaffoldState = rememberMemoListScaffoldState(),
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    val pagingItems = listStateHolder.pagingData.collectAsLazyPagingItems()
    val effect by listStateHolder.effect.collectAsStateWithLifecycle()

    MemoListScaffold(
        state = state,
        pagingItems = pagingItems,
        onAdd = onAdd,
        onMemo = onMemo,
        onFinishMemo = listStateHolder::finish,
        onDeleteMemo = listStateHolder::delete,
        modifier = modifier.shortcutFocus(state.focusRequester)
            .onAddShortcut(onAdd),
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
    )

    ShortcutFocusEffect(state = state)

    MemoListScaffoldEffect(
        state = state,
        effectProvider = { effect },
        onRestartMemo = listStateHolder::restart,
        onRestoreMemo = listStateHolder::restore,
        onEffect = listStateHolder::clearEffect,
    )

    PagingReloadEffect(pagingItems = pagingItems)

    PagingErrorMessageEffect(
        hostState = state.hostState,
        pagingItems = pagingItems,
    )
}

@Composable
private fun MemoListScaffold(
    state: MemoListScaffoldState,
    pagingItems: LazyPagingItems<Memo>,
    onAdd: () -> Unit,
    onMemo: (Uuid) -> Unit,
    onFinishMemo: (Uuid) -> Unit,
    onDeleteMemo: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                title = title,
                navigationIcon = navigationIcon,
                actions = actions,
            )
        },
        snackbarHost = { SnackbarHost(hostState = state.hostState) },
        floatingActionButton = { FloatingAddButton(onClick = onAdd) },
    ) { paddingValues ->
        PullToRefreshLazyColumn(
            pagingItems = pagingItems,
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues),
            state = state.lazyListState,
            empty = {
                Text(
                    text = "메모가 없습니다",
                    style = DiaryTheme.typography.titleMedium,
                )
            },
        ) {
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey { it.id.toString() },
                contentType = pagingItems.itemContentType { "Memo" },
            ) {
                val positionalThreshold = SwipeToDismissBoxDefaults.positionalThreshold
                val state = remember { SwipeToDismissBoxState(initialValue = SwipeToDismissBoxValue.Settled, positionalThreshold = positionalThreshold) }
                val uiState = pagingItems[it]

                FinishAndDeleteBox(
                    state = state,
                    onFinish = { uiState?.id?.let(onFinishMemo) },
                    onDelete = { uiState?.id?.let(onDeleteMemo) },
                    modifier = Modifier.fillParentMaxWidth()
                        .animateItem(),
                ) {
                    MemoListCard(
                        onClick = { uiState?.id?.let(onMemo) },
                        uiState = uiState,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
    )
}

@Composable
private fun ShortcutFocusEffect(
    state: MemoListScaffoldState,
) {
    LifecycleResumeEffect(state.focusRequester) {
        state.focusRequester.requestFocus()
        onPauseOrDispose { }
    }
}

@Composable
private fun MemoListScaffoldEffect(
    state: MemoListScaffoldState,
    effectProvider: () -> MemoListEffect,
    onRestartMemo: (Uuid) -> Unit,
    onRestoreMemo: (Uuid) -> Unit,
    onEffect: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val effect = effectProvider()

    LaunchedEffect(effect) {
        when (effect) {
            is MemoListEffect.None -> Unit

            is MemoListEffect.FinishSuccess -> {
                coroutineScope.launch {
                    state.hostState.showSnackbarImmediateWithActions(
                        message = "메모 완료",
                        actionLabel = "취소",
                        onPerformed = { onRestartMemo(effect.memoId) },
                    )
                }
                onEffect()
            }

            is MemoListEffect.DeleteSuccess -> {
                coroutineScope.launch {
                    state.hostState.showSnackbarImmediateWithActions(
                        message = "메모 삭제",
                        actionLabel = "취소",
                        onPerformed = { onRestoreMemo(effect.memoId) },
                    )
                }
                onEffect()
            }

            is MemoListEffect.UnknownError -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("네트워크 상태를 확인하세요") }
                onEffect()
            }
        }
    }
}

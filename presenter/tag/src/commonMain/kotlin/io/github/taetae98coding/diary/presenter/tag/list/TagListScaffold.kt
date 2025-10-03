package io.github.taetae98coding.diary.presenter.tag.list

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
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.library.compose.foundation.shortcutFocus
import io.github.taetae98coding.diary.library.compose.ui.onAddShortcut
import kotlin.uuid.Uuid
import kotlinx.coroutines.launch

@Composable
public fun TagListScaffold(
    listStateHolder: TagListStateHolder,
    onAdd: () -> Unit,
    onTag: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    state: TagListScaffoldState = rememberTagListScaffoldState(),
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
) {
    val pagingItems = listStateHolder.pagingData.collectAsLazyPagingItems()
    val effect by listStateHolder.effect.collectAsStateWithLifecycle()

    TagListScaffold(
        state = state,
        pagingItems = pagingItems,
        onAdd = onAdd,
        onTag = onTag,
        onFinishTag = listStateHolder::finish,
        onDeleteTag = listStateHolder::delete,
        modifier = modifier.shortcutFocus(state.focusRequester)
            .onAddShortcut(onAdd),
        title = title,
        navigationIcon = navigationIcon,
    )

    ShortcutFocusEffect(state = state)

    TagListScaffoldEffect(
        state = state,
        effectProvider = { effect },
        onRestartTag = listStateHolder::restart,
        onRestoreTag = listStateHolder::restore,
        onEffect = listStateHolder::clearEffect,
    )

    PagingReloadEffect(pagingItems = pagingItems)

    PagingErrorMessageEffect(
        hostState = state.hostState,
        pagingItems = pagingItems,
    )
}

@Composable
private fun TagListScaffold(
    state: TagListScaffoldState,
    pagingItems: LazyPagingItems<Tag>,
    onAdd: () -> Unit,
    onTag: (Uuid) -> Unit,
    onFinishTag: (Uuid) -> Unit,
    onDeleteTag: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                title = title,
                navigationIcon = navigationIcon,
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
                    text = "태그가 없습니다",
                    style = DiaryTheme.typography.titleMedium,
                )
            },
        ) {
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey { it.id.toString() },
                contentType = pagingItems.itemContentType { "Tag" },
            ) {
                val positionalThreshold = SwipeToDismissBoxDefaults.positionalThreshold
                val state = remember {
                    SwipeToDismissBoxState(
                        initialValue = SwipeToDismissBoxValue.Settled,
                        positionalThreshold = positionalThreshold,
                    )
                }
                val uiState = pagingItems[it]

                FinishAndDeleteBox(
                    state = state,
                    onFinish = { uiState?.id?.let(onFinishTag) },
                    onDelete = { uiState?.id?.let(onDeleteTag) },
                    modifier = Modifier.fillParentMaxWidth()
                        .animateItem(),
                ) {
                    TagListCard(
                        onClick = { uiState?.id?.let(onTag) },
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
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = navigationIcon,
    )
}

@Composable
private fun ShortcutFocusEffect(
    state: TagListScaffoldState,
) {
    LifecycleResumeEffect(state.focusRequester) {
        state.focusRequester.requestFocus()
        onPauseOrDispose { }
    }
}

@Composable
private fun TagListScaffoldEffect(
    state: TagListScaffoldState,
    effectProvider: () -> TagListEffect,
    onRestartTag: (Uuid) -> Unit,
    onRestoreTag: (Uuid) -> Unit,
    onEffect: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val effect = effectProvider()

    LaunchedEffect(effect) {
        when (effect) {
            is TagListEffect.None -> Unit

            is TagListEffect.FinishSuccess -> {
                coroutineScope.launch {
                    state.hostState.showSnackbarImmediateWithActions(
                        message = "태그 완료",
                        actionLabel = "취소",
                        onPerformed = { onRestartTag(effect.tagId) },
                    )
                }
                onEffect()
            }

            is TagListEffect.DeleteSuccess -> {
                coroutineScope.launch {
                    state.hostState.showSnackbarImmediateWithActions(
                        message = "태그 삭제",
                        actionLabel = "취소",
                        onPerformed = { onRestoreTag(effect.tagId) },
                    )
                }
                onEffect()
            }

            is TagListEffect.UnknownError -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("네트워크 상태를 확인하세요") }
                onEffect()
            }
        }
    }
}

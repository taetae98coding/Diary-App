package io.github.taetae98coding.diary.presenter.tag.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import io.github.taetae98coding.diary.compose.core.box.EmptyBox
import io.github.taetae98coding.diary.compose.core.box.FinishAndDeleteSwipeBox
import io.github.taetae98coding.diary.compose.core.box.LoadingBox
import io.github.taetae98coding.diary.compose.core.button.FloatingAddButton
import io.github.taetae98coding.diary.compose.core.effect.PlatformRefreshLifecycleEffect
import io.github.taetae98coding.diary.compose.core.snackbar.showSnackbarImmediate
import io.github.taetae98coding.diary.compose.core.snackbar.showSnackbarImmediateWithActions
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.library.compose.foundation.shortcutFocus
import io.github.taetae98coding.diary.library.compose.ui.onAddShortcut
import io.github.taetae98coding.diary.library.paging.compose.isEmptyVisible
import io.github.taetae98coding.diary.library.paging.compose.isLoadingVisible
import kotlin.uuid.Uuid
import kotlinx.coroutines.launch

@Composable
public fun TagListScaffold(
    listStateHolder: TagListStateHolder,
    onAdd: () -> Unit,
    onTag: (Uuid) -> Unit,
    onTagMemo: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    state: TagListScaffoldState = rememberTagListScaffoldState(),
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
) {
    TagListScaffold(
        state = state,
        pagingItems = listStateHolder.pagingData.collectAsLazyPagingItems(),
        onAdd = onAdd,
        onTag = onTag,
        onTagMemo = onTagMemo,
        onFinishTag = listStateHolder::finish,
        onDeleteTag = listStateHolder::delete,
        modifier = modifier.shortcutFocus(state.focusRequester)
            .onAddShortcut(onAdd),
        title = title,
        navigationIcon = navigationIcon,
    )

    ShortcutFocusEffect(state = state)

    FetchEffect(stateHolder = listStateHolder)

    TagListScaffoldEffect(
        state = state,
        stateHolder = listStateHolder,
    )
}

@Composable
private fun TagListScaffold(
    state: TagListScaffoldState,
    pagingItems: LazyPagingItems<Tag>,
    onAdd: () -> Unit,
    onTag: (Uuid) -> Unit,
    onTagMemo: (Uuid) -> Unit,
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
        val uiState = when {
            pagingItems.isLoadingVisible() -> TagListScreenUiState.Loading
            pagingItems.isEmptyVisible() -> TagListScreenUiState.Empty
            else -> TagListScreenUiState.Content
        }

        AnimatedContent(
            targetState = uiState,
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues),
        ) { value ->
            when (value) {
                TagListScreenUiState.Loading -> {
                    LoadingBox(modifier = Modifier.fillMaxSize())
                }

                TagListScreenUiState.Empty -> {
                    EmptyBox(modifier = Modifier.fillMaxSize()) {
                        Text(text = "태그가 없습니다")
                    }
                }

                TagListScreenUiState.Content -> {
                    VerticalGrid(
                        state = state,
                        pagingItems = pagingItems,
                        onTag = onTag,
                        onTagMemo = onTagMemo,
                        onFinishTag = onFinishTag,
                        onDeleteTag = onDeleteTag,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

private enum class TagListScreenUiState {
    Loading,
    Empty,
    Content,
}

@Composable
private fun VerticalGrid(
    state: TagListScaffoldState,
    pagingItems: LazyPagingItems<Tag>,
    onTag: (Uuid) -> Unit,
    onTagMemo: (Uuid) -> Unit,
    onFinishTag: (Uuid) -> Unit,
    onDeleteTag: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        state = state.lazyGridState,
        contentPadding = PaddingValues(DiaryTheme.dimens.itemSpace),
        verticalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace),
        horizontalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace),
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

            FinishAndDeleteSwipeBox(
                state = state,
                onFinish = { uiState?.id?.let(onFinishTag) },
                onDelete = { uiState?.id?.let(onDeleteTag) },
                modifier = Modifier.fillMaxWidth()
                    .animateItem(),
            ) {
                TagListCard(
                    onClick = { uiState?.id?.let(onTag) },
                    onMemo = { uiState?.id?.let(onTagMemo) },
                    uiState = uiState,
                    modifier = Modifier.fillMaxWidth(),
                )
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
private fun FetchEffect(
    stateHolder: TagListStateHolder,
) {
    PlatformRefreshLifecycleEffect(stateHolder) {
        stateHolder.fetch()
    }
}

@Composable
private fun TagListScaffoldEffect(
    state: TagListScaffoldState,
    stateHolder: TagListStateHolder,
) {
    val coroutineScope = rememberCoroutineScope()
    val effect by stateHolder.effect.collectAsStateWithLifecycle()

    LaunchedEffect(state, stateHolder, effect) {
        when (val effect = effect) {
            is TagListEffect.None -> Unit

            is TagListEffect.FinishSuccess -> {
                coroutineScope.launch {
                    state.hostState.showSnackbarImmediateWithActions(
                        message = "태그 완료",
                        actionLabel = "취소",
                        onPerformed = { stateHolder.restart(effect.tagId) },
                    )
                }
                stateHolder.clearEffect()
            }

            is TagListEffect.DeleteSuccess -> {
                coroutineScope.launch {
                    state.hostState.showSnackbarImmediateWithActions(
                        message = "태그 삭제",
                        actionLabel = "취소",
                        onPerformed = { stateHolder.restore(effect.tagId) },
                    )
                }
                stateHolder.clearEffect()
            }

            is TagListEffect.FetchError -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("오프라인 상태입니다") }
                stateHolder.clearEffect()
            }

            is TagListEffect.UnknownError -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("네트워크 상태를 확인하세요") }
                stateHolder.clearEffect()
            }
        }
    }
}

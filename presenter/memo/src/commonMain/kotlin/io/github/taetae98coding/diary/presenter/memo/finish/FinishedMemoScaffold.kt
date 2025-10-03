package io.github.taetae98coding.diary.presenter.memo.finish

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import io.github.taetae98coding.diary.compose.core.box.DeleteSwipeBox
import io.github.taetae98coding.diary.compose.core.box.EmptyBox
import io.github.taetae98coding.diary.compose.core.box.LoadingBox
import io.github.taetae98coding.diary.compose.core.icon.NavigateUpIcon
import io.github.taetae98coding.diary.compose.core.snackbar.showSnackbarImmediate
import io.github.taetae98coding.diary.compose.core.snackbar.showSnackbarImmediateWithActions
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.compose.memo.card.MemoListCard
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.library.paging.compose.isEmptyVisible
import io.github.taetae98coding.diary.library.paging.compose.isLoadingVisible
import kotlin.uuid.Uuid
import kotlinx.coroutines.launch

@Composable
public fun FinishedMemoScaffold(
    stateHolder: FinishedMemoStateHolder,
    onNavigateUp: () -> Unit,
    onMemo: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberFinishedMemoScaffoldState()

    FinishedMemoScaffold(
        state = state,
        pagingItems = stateHolder.pagingData.collectAsLazyPagingItems(),
        onNavigateUp = onNavigateUp,
        onMemo = onMemo,
        onDeleteMemo = stateHolder::delete,
        modifier = modifier,
    )

    FinishedMemoScaffoldEffect(
        state = state,
        stateHolder = stateHolder,
    )
}

@Composable
private fun FinishedMemoScaffold(
    state: FinishedMemoScaffoldState,
    pagingItems: LazyPagingItems<Memo>,
    onNavigateUp: () -> Unit,
    onMemo: (Uuid) -> Unit,
    onDeleteMemo: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopBar(onNavigateUp = onNavigateUp) },
        snackbarHost = { SnackbarHost(hostState = state.hostState) },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(DiaryTheme.dimens.itemSpace),
            verticalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace),
        ) {
            if (pagingItems.isLoadingVisible()) {
                item(
                    key = LoadingBox.KEY,
                    contentType = LoadingBox.ContentType,
                ) {
                    LoadingBox(
                        modifier = Modifier.fillParentMaxSize()
                            .animateItem(),
                    )
                }
            } else if (pagingItems.isEmptyVisible()) {
                item(
                    key = EmptyBox.KEY,
                    contentType = EmptyBox.ContentType,
                ) {
                    EmptyBox(
                        modifier = Modifier.fillParentMaxSize()
                            .animateItem(),
                    ) {
                        Text(text = "메모가 없습니다")
                    }
                }
            } else {
                items(
                    count = pagingItems.itemCount,
                    key = pagingItems.itemKey { it.id.toString() },
                    contentType = pagingItems.itemContentType { "Memo" },
                ) {
                    val positionalThreshold = SwipeToDismissBoxDefaults.positionalThreshold
                    val state = remember { SwipeToDismissBoxState(initialValue = SwipeToDismissBoxValue.Settled, positionalThreshold = positionalThreshold) }
                    val uiState = pagingItems[it]

                    DeleteSwipeBox(
                        state = state,
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
}

@Composable
private fun FinishedMemoScaffoldEffect(
    state: FinishedMemoScaffoldState,
    stateHolder: FinishedMemoStateHolder,
) {
    val coroutineScope = rememberCoroutineScope()
    val effect by stateHolder.effect.collectAsStateWithLifecycle()

    LaunchedEffect(stateHolder, effect) {
        when (val e = effect) {
            is FinishedMemoEffect.None -> Unit
            is FinishedMemoEffect.DeleteSuccess -> {
                coroutineScope.launch {
                    state.hostState.showSnackbarImmediateWithActions(
                        message = "메모 삭제",
                        actionLabel = "취소",
                        onPerformed = { stateHolder.restore(e.memoId) },
                    )
                }
                stateHolder.clearEffect()
            }

            is FinishedMemoEffect.UnknownError -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("네트워크 상태를 확인하세요") }
                stateHolder.clearEffect()
            }
        }
    }
}

@Composable
private fun TopBar(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = "완료된 메모") },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                NavigateUpIcon()
            }
        },
    )
}

package io.github.taetae98coding.diary.presenter.tag.finish

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
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.library.paging.compose.isEmptyVisible
import io.github.taetae98coding.diary.library.paging.compose.isLoadingVisible
import io.github.taetae98coding.diary.presenter.tag.list.TagListCard
import kotlin.uuid.Uuid
import kotlinx.coroutines.launch

@Composable
public fun FinishedTagScaffold(
    stateHolder: FinishedTagStateHolder,
    onNavigateUp: () -> Unit,
    onTag: (Uuid) -> Unit,
    onTagMemo: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberFinishedTagScaffoldState()

    FinishedTagScaffold(
        state = state,
        pagingItems = stateHolder.pagingData.collectAsLazyPagingItems(),
        onNavigateUp = onNavigateUp,
        onTag = onTag,
        onTagMemo = onTagMemo,
        onDeleteTag = stateHolder::delete,
        modifier = modifier,
    )

    FinishedTagScaffoldEffect(
        state = state,
        stateHolder = stateHolder,
    )
}

@Composable
private fun FinishedTagScaffold(
    state: FinishedTagScaffoldState,
    pagingItems: LazyPagingItems<Tag>,
    onNavigateUp: () -> Unit,
    onTag: (Uuid) -> Unit,
    onTagMemo: (Uuid) -> Unit,
    onDeleteTag: (Uuid) -> Unit,
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
                        Text(text = "태그가 없습니다")
                    }
                }
            } else {
                items(
                    count = pagingItems.itemCount,
                    key = pagingItems.itemKey { it.id.toString() },
                    contentType = pagingItems.itemContentType { "Tag" },
                ) {
                    val positionalThreshold = SwipeToDismissBoxDefaults.positionalThreshold
                    val state = remember { SwipeToDismissBoxState(initialValue = SwipeToDismissBoxValue.Settled, positionalThreshold = positionalThreshold) }
                    val uiState = pagingItems[it]

                    DeleteSwipeBox(
                        state = state,
                        onDelete = { uiState?.id?.let(onDeleteTag) },
                        modifier = Modifier.fillParentMaxWidth()
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
    }
}

@Composable
private fun FinishedTagScaffoldEffect(
    state: FinishedTagScaffoldState,
    stateHolder: FinishedTagStateHolder,
) {
    val coroutineScope = rememberCoroutineScope()
    val effect by stateHolder.effect.collectAsStateWithLifecycle()

    LaunchedEffect(stateHolder, effect) {
        when (val e = effect) {
            is FinishedTagEffect.None -> Unit
            is FinishedTagEffect.DeleteSuccess -> {
                coroutineScope.launch {
                    state.hostState.showSnackbarImmediateWithActions(
                        message = "태그 삭제",
                        actionLabel = "취소",
                        onPerformed = { stateHolder.restore(e.tagId) },
                    )
                }
                stateHolder.clearEffect()
            }

            is FinishedTagEffect.UnknownError -> {
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
        title = { Text(text = "완료된 태그") },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                NavigateUpIcon()
            }
        },
    )
}

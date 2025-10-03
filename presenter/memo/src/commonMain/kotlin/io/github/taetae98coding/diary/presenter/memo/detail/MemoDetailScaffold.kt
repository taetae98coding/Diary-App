package io.github.taetae98coding.diary.presenter.memo.detail

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.animateFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.taetae98coding.diary.compose.core.button.FloatingCheckButton
import io.github.taetae98coding.diary.compose.core.card.calendar.CalendarCard
import io.github.taetae98coding.diary.compose.core.card.calendar.CalendarCardDatePickerHost
import io.github.taetae98coding.diary.compose.core.card.color.ColorCard
import io.github.taetae98coding.diary.compose.core.card.color.ColorCardColorPickerHost
import io.github.taetae98coding.diary.compose.core.card.description.DescriptionCard
import io.github.taetae98coding.diary.compose.core.card.title.TitleCard
import io.github.taetae98coding.diary.compose.core.effect.PlatformRefreshLifecycleEffect
import io.github.taetae98coding.diary.compose.core.icon.DeleteIcon
import io.github.taetae98coding.diary.compose.core.icon.FinishIcon
import io.github.taetae98coding.diary.compose.core.icon.NavigateUpIcon
import io.github.taetae98coding.diary.compose.core.snackbar.showSnackbarImmediate
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.core.entity.memo.PrimaryMemoTag
import io.github.taetae98coding.diary.library.compose.ui.onActionShortcut
import io.github.taetae98coding.diary.presenter.memo.compose.MemoTagCard
import io.github.taetae98coding.diary.presenter.memo.tag.MemoTagStateHolder
import kotlin.uuid.Uuid
import kotlinx.coroutines.launch

@Composable
public fun MemoDetailScaffold(
    memoDetailStateHolder: MemoDetailStateHolder,
    memoTagStateHolder: MemoTagStateHolder,
    onNavigateUp: () -> Unit,
    onTagTitle: () -> Unit,
    onTag: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    val detail by memoDetailStateHolder.detail.collectAsStateWithLifecycle()
    val finishUiState by memoDetailStateHolder.finishUiState.collectAsStateWithLifecycle()
    val floatingUiState by memoDetailStateHolder.floatingUiState.collectAsStateWithLifecycle()

    val state = rememberMemoDetailScaffoldState(
        detailProvider = { detail },
    )

    val primaryMemoTagUiStatePagingItems = memoTagStateHolder.primaryMemoTagPagingData.collectAsLazyPagingItems()

    MemoDetailScaffold(
        state = state,
        primaryMemoTagUiStatePagingItems = primaryMemoTagUiStatePagingItems,
        detailProvider = { detail },
        finishUiStateProvider = { finishUiState },
        floatingUiStateProvider = { floatingUiState },
        onNavigateUp = onNavigateUp,
        onTagTitle = onTagTitle,
        onTag = onTag,
        onFinish = memoDetailStateHolder::finish,
        onRestart = memoDetailStateHolder::restart,
        onDelete = memoDetailStateHolder::delete,
        onUpdate = { memoDetailStateHolder.update(state.detailState.detail) },
        modifier = modifier.onActionShortcut { memoDetailStateHolder.update(state.detailState.detail) },
    )

    FetchEffect(stateHolder = memoDetailStateHolder)

    MemoDetailScaffoldEffect(
        state = state,
        stateHolder = memoDetailStateHolder,
        navigateUp = onNavigateUp,
    )
}

@Composable
private fun MemoDetailScaffold(
    state: MemoDetailScaffoldState,
    primaryMemoTagUiStatePagingItems: LazyPagingItems<PrimaryMemoTag>,
    detailProvider: () -> MemoDetail?,
    finishUiStateProvider: () -> MemoDetailFinishUiState,
    floatingUiStateProvider: () -> MemoDetailFloatingUiState,
    onNavigateUp: () -> Unit,
    onTagTitle: () -> Unit,
    onTag: (Uuid) -> Unit,
    onFinish: () -> Unit,
    onRestart: () -> Unit,
    onDelete: () -> Unit,
    onUpdate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                detailProvider = detailProvider,
                finishUiStateProvider = finishUiStateProvider,
                onNavigateUp = onNavigateUp,
                onFinish = onFinish,
                onRestart = onRestart,
                onDelete = onDelete,
            )
        },
        snackbarHost = { SnackbarHost(hostState = state.hostState) },
        floatingActionButton = {
            val isVisible by remember(state) {
                derivedStateOf {
                    detailProvider()?.let { it != state.detailState.detail } ?: false
                }
            }

            FloatingCheckButton(
                onClick = onUpdate,
                modifier = Modifier.animateFloatingActionButton(
                    visible = isVisible,
                    alignment = Alignment.Center,
                ),
                isInProgressProvider = { floatingUiStateProvider().isInProgress },
            )
        },
    ) {
        val isLoading by remember { derivedStateOf { detailProvider() == null } }

        Crossfade(
            targetState = isLoading,
            modifier = Modifier.fillMaxSize()
                .padding(it),
        ) { isLoading ->
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    ContainedLoadingIndicator()
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = DiaryTheme.dimens.screenPaddingValues,
                    verticalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace),
                    horizontalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace),
                ) {
                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        TitleCard(
                            state = state.detailState.titleCardState,
                            nextFocusProperty = state.detailState.descriptionCardState.textFieldFocusRequester ?: FocusRequester.Default,
                        )
                    }

                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        DescriptionCard(
                            state = state.detailState.descriptionCardState,
                            previousFocusProperty = state.detailState.titleCardState.focusRequester,
                        )
                    }

                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        CalendarCard(state = state.detailState.calendarCardState)
                    }

                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        MemoTagCard(
                            pagingItems = primaryMemoTagUiStatePagingItems,
                            onTagTitle = onTagTitle,
                            onTag = onTag,
                            modifier = Modifier.height(200.dp),
                        )
                    }

                    item {
                        ColorCard(
                            state = state.detailState.colorCardState,
                        )
                    }
                }
            }
        }
    }

    CalendarCardDatePickerHost(
        state = state.detailState.calendarCardState,
    )

    ColorCardColorPickerHost(
        state = state.detailState.colorCardState,
    )
}

@Composable
private fun TopBar(
    detailProvider: () -> MemoDetail?,
    finishUiStateProvider: () -> MemoDetailFinishUiState,
    onNavigateUp: () -> Unit,
    onFinish: () -> Unit,
    onRestart: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(
                text = detailProvider()?.title ?: "메모",
                modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
                maxLines = 1,
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                NavigateUpIcon()
            }
        },
        actions = {
            val finishUiState = finishUiStateProvider()

            IconToggleButton(
                checked = finishUiState.isFinished,
                onCheckedChange = { isFinish ->
                    if (isFinish) {
                        onFinish()
                    } else {
                        onRestart()
                    }
                },
            ) {
                FinishIcon()
            }
            IconButton(
                onClick = onDelete,
            ) {
                DeleteIcon()
            }
        },
    )
}

@Composable
private fun FetchEffect(
    stateHolder: MemoDetailStateHolder,
) {
    PlatformRefreshLifecycleEffect(stateHolder) {
        stateHolder.fetch()
    }
}

@Composable
private fun MemoDetailScaffoldEffect(
    state: MemoDetailScaffoldState,
    stateHolder: MemoDetailStateHolder,
    navigateUp: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val effect by stateHolder.effect.collectAsStateWithLifecycle()

    LaunchedEffect(state, stateHolder, effect) {
        when (effect) {
            MemoDetailEffect.None -> Unit

            MemoDetailEffect.UpdateFinish -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("메모 업데이트") }
                stateHolder.clearEffect()
            }

            MemoDetailEffect.DeleteFinish -> {
                navigateUp()
                stateHolder.clearEffect()
            }

            MemoDetailEffect.FetchError -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("오프라인 상태입니다") }
                stateHolder.clearEffect()
            }

            MemoDetailEffect.UnknownError -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("네트워크 상태를 확인하세요") }
                stateHolder.clearEffect()
            }
        }
    }
}

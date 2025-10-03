package io.github.taetae98coding.diary.feature.buddy.group.detail

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.IconButton
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
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.taetae98coding.diary.compose.core.button.FloatingCheckButton
import io.github.taetae98coding.diary.compose.core.card.description.DescriptionCard
import io.github.taetae98coding.diary.compose.core.card.title.TitleCard
import io.github.taetae98coding.diary.compose.core.effect.PagingErrorMessageEffect
import io.github.taetae98coding.diary.compose.core.effect.PagingReloadEffect
import io.github.taetae98coding.diary.compose.core.effect.PlatformRefreshLifecycleEffect
import io.github.taetae98coding.diary.compose.core.icon.NavigateUpIcon
import io.github.taetae98coding.diary.compose.core.snackbar.showSnackbarImmediate
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.core.entity.buddy.Buddy
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroupDetail
import io.github.taetae98coding.diary.feature.buddy.group.card.BuddyCard
import io.github.taetae98coding.diary.library.compose.ui.onActionShortcut
import kotlinx.coroutines.launch

@Composable
internal fun BuddyGroupDetailScreen(
    navigateUp: () -> Unit,
    navigateToBuddyGroupMemoList: () -> Unit,
    navigateToBuddyGroupTag: () -> Unit,
    navigateToBuddyGroupCalendar: () -> Unit,
    navigateToBuddyGroupFinishedMemo: () -> Unit,
    navigateToBuddyGroupFinishedTag: () -> Unit,
    detailViewModel: BuddyGroupDetailViewModel,
    modifier: Modifier = Modifier,
) {
    val detail by detailViewModel.detail.collectAsStateWithLifecycle()

    val state = rememberBuddyGroupDetailScreenState(
        detailProvider = { detail },
    )

    val buddyPagingItems = detailViewModel.buddyPagingData.collectAsLazyPagingItems()

    BuddyGroupDetailScreen(
        state = state,
        buddyPagingItems = buddyPagingItems,
        detailProvider = { detail },
        onNavigateUp = dropUnlessResumed { navigateUp() },
        onUpdate = { detailViewModel.update(state.detailState.detail) },
        onBuddyTitle = { /* TODO 버디 수정 기능 */ },
        onMemo = dropUnlessResumed { navigateToBuddyGroupMemoList() },
        onTag = dropUnlessResumed { navigateToBuddyGroupTag() },
        onCalendar = dropUnlessResumed { navigateToBuddyGroupCalendar() },
        onFinishedMemo = dropUnlessResumed { navigateToBuddyGroupFinishedMemo() },
        onFinishedTag = dropUnlessResumed { navigateToBuddyGroupFinishedTag() },
        modifier = modifier.onActionShortcut { detailViewModel.update(state.detailState.detail) },
    )

    FetchEffect(detailViewModel = detailViewModel)

    BuddyGroupDetailScreenEffect(
        state = state,
        detailViewModel = detailViewModel,
    )

    PagingReloadEffect(pagingItems = buddyPagingItems)

    PagingErrorMessageEffect(
        hostState = state.hostState,
        pagingItems = buddyPagingItems,
    )
}

@Composable
private fun BuddyGroupDetailScreen(
    state: BuddyGroupDetailScreenState,
    buddyPagingItems: LazyPagingItems<Buddy>,
    detailProvider: () -> BuddyGroupDetail?,
    onNavigateUp: () -> Unit,
    onUpdate: () -> Unit,
    onBuddyTitle: () -> Unit,
    onMemo: () -> Unit,
    onTag: () -> Unit,
    onCalendar: () -> Unit,
    onFinishedMemo: () -> Unit,
    onFinishedTag: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                detailProvider = detailProvider,
                onNavigateUp = onNavigateUp,
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
                LoadingBox(
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                Content(
                    state = state,
                    buddyPagingItems = buddyPagingItems,
                    onBuddyTitle = onBuddyTitle,
                    onMemo = onMemo,
                    onTag = onTag,
                    onCalendar = onCalendar,
                    onFinishedMemo = onFinishedMemo,
                    onFinishedTag = onFinishedTag,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun TopBar(
    detailProvider: () -> BuddyGroupDetail?,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(
                text = detailProvider()?.title ?: "버디 그룹",
                modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
                maxLines = 1,
            )
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                NavigateUpIcon()
            }
        },
    )
}

@Composable
private fun LoadingBox(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        ContainedLoadingIndicator()
    }
}

@Composable
private fun Content(
    state: BuddyGroupDetailScreenState,
    buddyPagingItems: LazyPagingItems<Buddy>,
    onBuddyTitle: () -> Unit,
    onMemo: () -> Unit,
    onTag: () -> Unit,
    onCalendar: () -> Unit,
    onFinishedMemo: () -> Unit,
    onFinishedTag: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
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
            BuddyCard(
                pagingItems = buddyPagingItems,
                onTitle = onBuddyTitle,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        item {
            DetailCard(
                text = "메모",
                onClick = onMemo,
            )
        }

        item {
            DetailCard(
                text = "태그",
                onClick = onTag,
            )
        }

        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
            DetailCard(
                text = "캘린더",
                onClick = onCalendar,
            )
        }

        item {
            DetailCard(
                text = "완료된 메모",
                onClick = onFinishedMemo,
            )
        }

        item {
            DetailCard(
                text = "완료된 태그",
                onClick = onFinishedTag,
            )
        }
    }
}

@Composable
private fun DetailCard(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(28.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                style = DiaryTheme.typography.titleMediumEmphasized,
            )
        }
    }
}

@Composable
private fun FetchEffect(
    detailViewModel: BuddyGroupDetailViewModel,
) {
    PlatformRefreshLifecycleEffect(detailViewModel) {
        detailViewModel.fetch()
    }
}

@Composable
private fun BuddyGroupDetailScreenEffect(
    state: BuddyGroupDetailScreenState,
    detailViewModel: BuddyGroupDetailViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    val effect by detailViewModel.effect.collectAsStateWithLifecycle()

    LaunchedEffect(state, effect) {
        when (effect) {
            BuddyGroupDetailEffect.None -> Unit
            BuddyGroupDetailEffect.FetchFail -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("오프라인 상태입니다") }
                detailViewModel.clearEffect()
            }

            BuddyGroupDetailEffect.UpdateFinish -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("버디 그룹 업데이트") }
                detailViewModel.clearEffect()
            }

            BuddyGroupDetailEffect.UnknownError -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("네트워크 상태를 확인하세요") }
                detailViewModel.clearEffect()
            }
        }
    }
}

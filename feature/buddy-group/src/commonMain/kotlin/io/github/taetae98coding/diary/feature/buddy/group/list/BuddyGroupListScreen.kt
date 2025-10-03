package io.github.taetae98coding.diary.feature.buddy.group.list

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.taetae98coding.diary.compose.core.button.FloatingAddButton
import io.github.taetae98coding.diary.compose.core.effect.PagingErrorMessageEffect
import io.github.taetae98coding.diary.compose.core.effect.PagingReloadEffect
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroup
import io.github.taetae98coding.diary.feature.buddy.group.BuddyGroupScrollState
import io.github.taetae98coding.diary.library.compose.foundation.shortcutFocus
import io.github.taetae98coding.diary.library.compose.ui.onAddShortcut
import io.github.taetae98coding.diary.library.lifecycle.common.ifResumed
import kotlin.uuid.Uuid
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BuddyGroupListScreen(
    scrollState: BuddyGroupScrollState,
    navigateToLogin: () -> Unit,
    navigateToBuddyGroupAdd: () -> Unit,
    navigateToBuddyGroupDetail: (Uuid) -> Unit,
    navigateToBuddyGroupMemoList: (Uuid) -> Unit,
    navigateToBuddyGroupTagList: (Uuid) -> Unit,
    navigateToBuddyGroupCalendar: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    accountViewModel: BuddyGroupListAccountViewModel = koinViewModel(),
    listViewModel: BuddyGroupListViewModel = koinViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state = rememberBuddyGroupListScreenState()
    val accountUiState by accountViewModel.uiState.collectAsStateWithLifecycle()

    BuddyGroupListScreen(
        state = state,
        accountUiStateProvider = { accountUiState },
        pagingItems = listViewModel.pagingData.collectAsLazyPagingItems(),
        onAdd = dropUnlessResumed { navigateToBuddyGroupAdd() },
        onLogin = dropUnlessResumed { navigateToLogin() },
        onBuddyGroup = { lifecycleOwner.ifResumed { navigateToBuddyGroupDetail(it) } },
        onBuddyGroupMemo = { lifecycleOwner.ifResumed { navigateToBuddyGroupMemoList(it) } },
        onBuddyGroupTag = { lifecycleOwner.ifResumed { navigateToBuddyGroupTagList(it) } },
        onBuddyGroupCalendar = { lifecycleOwner.ifResumed { navigateToBuddyGroupCalendar(it) } },
        modifier = modifier.shortcutFocus(state.focusRequester)
            .onAddShortcut(navigateToBuddyGroupAdd),
    )

    ShortcutFocusEffect(state = state)

    ScrollEffect(
        state = state,
        scrollState = scrollState,
    )
}

@Composable
internal fun BuddyGroupListScreen(
    state: BuddyGroupListScreenState,
    accountUiStateProvider: () -> BuddyGroupListAccountUiState,
    pagingItems: LazyPagingItems<BuddyGroup>,
    onAdd: () -> Unit,
    onLogin: () -> Unit,
    onBuddyGroup: (Uuid) -> Unit,
    onBuddyGroupMemo: (Uuid) -> Unit,
    onBuddyGroupTag: (Uuid) -> Unit,
    onBuddyGroupCalendar: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopBar() },
        snackbarHost = { SnackbarHost(hostState = state.hostState) },
        floatingActionButton = {
            val isVisible by remember { derivedStateOf { accountUiStateProvider() is BuddyGroupListAccountUiState.User } }

            FloatingAddButton(
                onClick = onAdd,
                modifier = Modifier.animateFloatingActionButton(
                    visible = isVisible,
                    alignment = Alignment.Center,
                ),
            )
        },
    ) {
        Crossfade(
            targetState = accountUiStateProvider(),
            modifier = Modifier.fillMaxSize()
                .padding(it),
        ) { accountUiState ->
            when (accountUiState) {
                is BuddyGroupListAccountUiState.Guest -> {
                    BuddyGroupListGuestContent(
                        onLogin = onLogin,
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                is BuddyGroupListAccountUiState.User -> {
                    BuddyGroupListUserContent(
                        state = state,
                        pagingItems = pagingItems,
                        onBuddyGroup = onBuddyGroup,
                        onBuddyGroupMemo = onBuddyGroupMemo,
                        onBuddyGroupTag = onBuddyGroupTag,
                        onBuddyGroupCalendar = onBuddyGroupCalendar,
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                is BuddyGroupListAccountUiState.Loading -> Unit
            }
        }
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = "버디 그룹") },
    )
}

@Composable
private fun ShortcutFocusEffect(
    state: BuddyGroupListScreenState,
) {
    LifecycleResumeEffect(state.focusRequester) {
        state.focusRequester.requestFocus()
        onPauseOrDispose { }
    }
}

@Composable
private fun ScrollEffect(
    state: BuddyGroupListScreenState,
    scrollState: BuddyGroupScrollState,
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

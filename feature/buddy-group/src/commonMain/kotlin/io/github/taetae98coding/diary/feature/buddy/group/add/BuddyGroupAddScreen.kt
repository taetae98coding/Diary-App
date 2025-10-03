package io.github.taetae98coding.diary.feature.buddy.group.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.lifecycle.withResumed
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.taetae98coding.diary.compose.core.button.FloatingAddButton
import io.github.taetae98coding.diary.compose.core.card.description.DescriptionCard
import io.github.taetae98coding.diary.compose.core.card.title.TitleCard
import io.github.taetae98coding.diary.compose.core.icon.NavigateUpIcon
import io.github.taetae98coding.diary.compose.core.snackbar.showSnackbarImmediate
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.core.entity.buddy.Buddy
import io.github.taetae98coding.diary.feature.buddy.group.card.BuddyCard
import io.github.taetae98coding.diary.feature.buddy.group.search.BuddySearchBottomSheet
import io.github.taetae98coding.diary.feature.buddy.group.search.BuddySearchBottomSheetState
import io.github.taetae98coding.diary.feature.buddy.group.search.BuddySearchStateHolder
import io.github.taetae98coding.diary.feature.buddy.group.search.rememberBuddySearchBottomSheetState
import io.github.taetae98coding.diary.library.compose.ui.onActionShortcut
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BuddyGroupAddScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BuddyGroupAddViewModel = koinViewModel(),
) {
    val state = rememberBuddyGroupAddScreenState()

    val floatingUiState by viewModel.floatingUiState.collectAsStateWithLifecycle()
    val buddyPagingItems = viewModel.buddyPagingData.collectAsLazyPagingItems()

    BuddyGroupAddScreen(
        state = state,
        floatingUiStateProvider = { floatingUiState },
        buddyPagingItems = buddyPagingItems,
        onNavigateUp = dropUnlessResumed { navigateUp() },
        onAdd = { viewModel.add(state.detailState.detail) },
        onBuddyTitle = state::showBuddySearchDialog,
        modifier = modifier.onActionShortcut { viewModel.add(state.detailState.detail) },
    )

    BuddySearchBottomSheetHost(
        state = state,
        stateHolder = viewModel,
    )

    TitleFocusEffect(
        state = state,
    )

    BuddyGroupAddScreenEffect(
        state = state,
        addViewModel = viewModel,
    )
}

@Composable
private fun BuddySearchBottomSheetHost(
    state: BuddyGroupAddScreenState,
    stateHolder: BuddySearchStateHolder,
) {
    if (state.isBuddySearchDialogVisible) {
        val searchBottomSheetState = rememberBuddySearchBottomSheetState()

        BuddySearchBottomSheet(
            state = searchBottomSheetState,
            stateHolder = stateHolder,
            onDismissRequest = state::hideBuddySearchDialog,
        )

        BuddySearchEffect(
            state = searchBottomSheetState,
            search = stateHolder::search,
        )
    }
}

@Composable
private fun BuddyGroupAddScreen(
    state: BuddyGroupAddScreenState,
    floatingUiStateProvider: () -> BuddyGroupAddFloatingUiState,
    buddyPagingItems: LazyPagingItems<Buddy>,
    onNavigateUp: () -> Unit,
    onAdd: () -> Unit,
    onBuddyTitle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopBar(onNavigateUp = onNavigateUp) },
        snackbarHost = { SnackbarHost(hostState = state.hostState) },
        floatingActionButton = {
            FloatingAddButton(
                onClick = onAdd,
                isInProgressProvider = { floatingUiStateProvider().isInProgress },
            )
        },
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
                .padding(it),
            contentPadding = DiaryTheme.dimens.screenPaddingValues,
            verticalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace),
            horizontalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace),
        ) {
            item(
                span = { GridItemSpan(maxCurrentLineSpan) },
            ) {
                TitleCard(
                    state = state.detailState.titleCardState,
                    nextFocusProperty = state.detailState.descriptionCardState.textFieldFocusRequester ?: FocusRequester.Default,
                )
            }

            item(
                span = { GridItemSpan(maxCurrentLineSpan) },
            ) {
                DescriptionCard(
                    state = state.detailState.descriptionCardState,
                    previousFocusProperty = state.detailState.titleCardState.focusRequester,
                )
            }

            item(
                span = { GridItemSpan(maxCurrentLineSpan) },
            ) {
                BuddyCard(
                    pagingItems = buddyPagingItems,
                    onTitle = onBuddyTitle,
                    modifier = Modifier.fillMaxWidth(),
                )
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
        title = { Text(text = "버디 그룹 추가") },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                NavigateUpIcon()
            }
        },
    )
}

@Composable
private fun TitleFocusEffect(
    state: BuddyGroupAddScreenState,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(
        lifecycleOwner,
        state.detailState.titleCardState.focusRequester,
    ) {
        lifecycleOwner.withResumed {
            state.detailState.titleCardState.focusRequester.requestFocus()
        }
    }
}

@Composable
private fun BuddyGroupAddScreenEffect(
    state: BuddyGroupAddScreenState,
    addViewModel: BuddyGroupAddViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    val effect by addViewModel.effect.collectAsStateWithLifecycle()

    LaunchedEffect(state, addViewModel, effect) {
        when (effect) {
            is BuddyGroupAddEffect.None -> Unit
            is BuddyGroupAddEffect.AddSuccess -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("버디 그룹 추가") }
                state.detailState.titleCardState.textFieldState.clearText()
                state.detailState.descriptionCardState.textFieldState.clearText()
                state.detailState.titleCardState.focusRequester.requestFocus()
                addViewModel.clearEffect()
            }

            is BuddyGroupAddEffect.TitleBlank -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("제목을 입력해 주세요") }
                state.detailState.titleCardState.focusRequester.requestFocus()
                addViewModel.clearEffect()
            }

            is BuddyGroupAddEffect.BuddyEmpty -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("버디를 선택해주세요") }
                addViewModel.clearEffect()
            }

            is BuddyGroupAddEffect.UnknownError -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("네트워크 상태를 확인하세요") }
                addViewModel.clearEffect()
            }
        }
    }
}

@Composable
private fun BuddySearchEffect(
    state: BuddySearchBottomSheetState,
    search: (String) -> Unit,
) {
    LaunchedEffect(state.textFieldState.text) {
        search(state.textFieldState.text.toString())
    }
}

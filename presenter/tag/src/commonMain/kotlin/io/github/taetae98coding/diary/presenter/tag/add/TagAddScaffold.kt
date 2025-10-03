package io.github.taetae98coding.diary.presenter.tag.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.withResumed
import io.github.taetae98coding.diary.compose.core.button.FloatingAddButton
import io.github.taetae98coding.diary.compose.core.card.color.ColorCard
import io.github.taetae98coding.diary.compose.core.card.color.ColorCardColorPickerHost
import io.github.taetae98coding.diary.compose.core.card.description.DescriptionCard
import io.github.taetae98coding.diary.compose.core.card.title.TitleCard
import io.github.taetae98coding.diary.compose.core.icon.NavigateUpIcon
import io.github.taetae98coding.diary.compose.core.snackbar.showSnackbarImmediate
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.library.compose.color.randomColor
import io.github.taetae98coding.diary.library.compose.ui.onActionShortcut
import kotlinx.coroutines.launch

@Composable
public fun TagAddScaffold(
    addStateHolder: TagAddStateHolder,
    onNavigateUp: () -> Unit,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberTagAddScaffoldState()
    val floatingUiState by addStateHolder.floatingUiState.collectAsStateWithLifecycle()

    TagAddScaffold(
        state = state,
        floatingUiStateProvider = { floatingUiState },
        onNavigateUp = onNavigateUp,
        onAdd = { addStateHolder.add(state.detailState.detail) },
        title = title,
        modifier = modifier.onActionShortcut { addStateHolder.add(state.detailState.detail) },
    )

    TitleFocusEffect(
        state = state,
    )

    TagAddScaffoldEffect(
        state = state,
        stateHolder = addStateHolder,
    )
}

@Composable
private fun TagAddScaffold(
    state: TagAddScaffoldState,
    floatingUiStateProvider: () -> TagAddFloatingUiState,
    onNavigateUp: () -> Unit,
    onAdd: () -> Unit,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                onNavigateUp = onNavigateUp,
                title = title,
            )
        },
        snackbarHost = { SnackbarHost(hostState = state.hostState) },
        floatingActionButton = {
            FloatingAddButton(
                onClick = onAdd,
                isInProgressProvider = { floatingUiStateProvider().isInProgress },
            )
        },
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues),
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

            item {
                ColorCard(
                    state = state.detailState.colorCardState,
                )
            }
        }
    }

    ColorCardColorPickerHost(
        state = state.detailState.colorCardState,
    )
}

@Composable
private fun TopBar(
    onNavigateUp: () -> Unit,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = title,
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
    state: TagAddScaffoldState,
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
private fun TagAddScaffoldEffect(
    state: TagAddScaffoldState,
    stateHolder: TagAddStateHolder,
) {
    val coroutineScope = rememberCoroutineScope()
    val effect by stateHolder.effect.collectAsStateWithLifecycle()

    LaunchedEffect(state, stateHolder, effect) {
        when (effect) {
            TagAddEffect.None -> Unit
            TagAddEffect.AddSuccess -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("태그 추가") }
                state.detailState.titleCardState.textFieldState.clearText()
                state.detailState.descriptionCardState.textFieldState.clearText()
                state.detailState.titleCardState.focusRequester.requestFocus()
                state.detailState.colorCardState.animateUpdateColor(randomColor())
                stateHolder.clearEffect()
            }

            TagAddEffect.TitleBlank -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("제목을 입력해 주세요") }
                state.detailState.titleCardState.focusRequester.requestFocus()
                stateHolder.clearEffect()
            }

            TagAddEffect.UnknownError -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("네트워크 상태를 확인하세요") }
                stateHolder.clearEffect()
            }
        }
    }
}

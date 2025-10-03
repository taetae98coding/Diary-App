package io.github.taetae98coding.diary.presenter.tag.detail

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.taetae98coding.diary.compose.core.button.FloatingCheckButton
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
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.library.compose.ui.onActionShortcut
import kotlinx.coroutines.launch

@Composable
public fun TagDetailScaffold(
    detailStateHolder: TagDetailStateHolder,
    onNavigateUp: () -> Unit,
    onMemo: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val detail by detailStateHolder.detail.collectAsStateWithLifecycle()
    val finishUiState by detailStateHolder.finishUiState.collectAsStateWithLifecycle()
    val floatingUiState by detailStateHolder.floatingUiState.collectAsStateWithLifecycle()
    val state = rememberTagDetailScaffoldState(
        detailProvider = { detail },
    )

    TagDetailScaffold(
        state = state,
        detailProvider = { detail },
        finishUiStateProvider = { finishUiState },
        floatingUiStateProvider = { floatingUiState },
        onNavigateUp = onNavigateUp,
        onFinish = detailStateHolder::finish,
        onRestart = detailStateHolder::restart,
        onDelete = detailStateHolder::delete,
        onUpdate = { detailStateHolder.update(state.detailState.detail) },
        onMemo = onMemo,
        modifier = modifier.onActionShortcut { detailStateHolder.update(state.detailState.detail) },
    )

    FetchEffect(
        stateHolder = detailStateHolder,
    )

    TagDetailScaffoldEffect(
        state = state,
        stateHolder = detailStateHolder,
        navigateUp = onNavigateUp,
    )
}

@Composable
private fun TagDetailScaffold(
    state: TagDetailScaffoldState,
    detailProvider: () -> TagDetail?,
    finishUiStateProvider: () -> TagDetailFinishUiState,
    floatingUiStateProvider: () -> TagDetailFloatingUiState,
    onNavigateUp: () -> Unit,
    onFinish: () -> Unit,
    onRestart: () -> Unit,
    onDelete: () -> Unit,
    onUpdate: () -> Unit,
    onMemo: () -> Unit,
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
                        Row(
                            modifier = Modifier.height(IntrinsicSize.Max),
                            horizontalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace),
                        ) {
                            ColorCard(
                                state = state.detailState.colorCardState,
                                modifier = Modifier.weight(1F),
                            )
                            Card(
                                onClick = onMemo,
                                modifier = Modifier.weight(1F)
                                    .fillMaxHeight(),
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = "메모",
                                        style = DiaryTheme.typography.titleMedium,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    ColorCardColorPickerHost(
        state = state.detailState.colorCardState,
    )
}

@Composable
private fun TopBar(
    detailProvider: () -> TagDetail?,
    finishUiStateProvider: () -> TagDetailFinishUiState,
    onNavigateUp: () -> Unit,
    onFinish: () -> Unit,
    onRestart: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(
                text = detailProvider()?.title ?: "태그",
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
    stateHolder: TagDetailStateHolder,
) {
    PlatformRefreshLifecycleEffect(stateHolder) {
        stateHolder.fetch()
    }
}

@Composable
private fun TagDetailScaffoldEffect(
    state: TagDetailScaffoldState,
    stateHolder: TagDetailStateHolder,
    navigateUp: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val effect by stateHolder.effect.collectAsStateWithLifecycle()

    LaunchedEffect(state, stateHolder, effect) {
        when (effect) {
            TagDetailEffect.None -> Unit

            TagDetailEffect.UpdateFinish -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("태그 업데이트") }
                stateHolder.clearEffect()
            }

            TagDetailEffect.DeleteFinish -> {
                navigateUp()
                stateHolder.clearEffect()
            }

            TagDetailEffect.FetchError -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("오프라인 상태입니다") }
                stateHolder.clearEffect()
            }

            TagDetailEffect.UnknownError -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("네트워크 상태를 확인하세요") }
                stateHolder.clearEffect()
            }
        }
    }
}

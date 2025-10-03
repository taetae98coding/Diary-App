package io.github.taetae98coding.diary.presenter.memo.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.taetae98coding.diary.compose.core.effect.PagingErrorMessageEffect
import io.github.taetae98coding.diary.compose.core.effect.PagingReloadEffect
import io.github.taetae98coding.diary.compose.core.icon.AddIcon
import io.github.taetae98coding.diary.compose.core.icon.NavigateUpIcon
import io.github.taetae98coding.diary.compose.core.snackbar.showSnackbarImmediate
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.core.entity.memo.PrimaryMemoTag
import io.github.taetae98coding.diary.core.entity.tag.SelectMemoTag
import io.github.taetae98coding.diary.presenter.memo.compose.MemoTagChip
import io.github.taetae98coding.diary.presenter.memo.compose.TagCard
import io.github.taetae98coding.diary.presenter.memo.compose.TagChip
import kotlin.uuid.Uuid
import kotlinx.coroutines.launch

@Composable
internal fun MemoTagScaffold(
    memoTagStateHolder: MemoTagStateHolder,
    onNavigateUp: () -> Unit,
    onTagAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberMemoTagScaffoldState()
    val effect by memoTagStateHolder.memoTagEffect.collectAsStateWithLifecycle()
    val primaryMemoTagPagingItems = memoTagStateHolder.primaryMemoTagPagingData.collectAsLazyPagingItems()
    val selectMemoTagPagingItems = memoTagStateHolder.selectMemoTagPagingData.collectAsLazyPagingItems()

    MemoTagScaffold(
        state = state,
        primaryMemoTagPagingItems = primaryMemoTagPagingItems,
        selectMemoTagPagingItems = selectMemoTagPagingItems,
        onNavigateUp = onNavigateUp,
        onTagAdd = onTagAdd,
        onSelectPrimaryTag = memoTagStateHolder::selectPrimaryTag,
        onRemovePrimaryTag = memoTagStateHolder::removePrimaryTag,
        onSelectMemoTag = memoTagStateHolder::selectMemoTag,
        onUnselectMemoTag = memoTagStateHolder::unselectMemoTag,
        modifier = modifier,
    )

    MemoTagScaffoldEffect(
        state = state,
        effectProvider = { effect },
        onEffect = memoTagStateHolder::clearMemoTagEffect,
    )

    PagingReloadEffect(pagingItems = selectMemoTagPagingItems)

    PagingErrorMessageEffect(
        hostState = state.hostState,
        pagingItems = selectMemoTagPagingItems,
    )
}

@Composable
private fun MemoTagScaffold(
    state: MemoTagScaffoldState,
    primaryMemoTagPagingItems: LazyPagingItems<PrimaryMemoTag>,
    selectMemoTagPagingItems: LazyPagingItems<SelectMemoTag>,
    onNavigateUp: () -> Unit,
    onTagAdd: () -> Unit,
    onSelectPrimaryTag: (Uuid) -> Unit,
    onRemovePrimaryTag: () -> Unit,
    onSelectMemoTag: (Uuid) -> Unit,
    onUnselectMemoTag: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopBar(onNavigateUp = onNavigateUp) },
        snackbarHost = { SnackbarHost(hostState = state.hostState) },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
                .padding(DiaryTheme.dimens.screenPaddingValues),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TagCard(
                pagingItems = primaryMemoTagPagingItems,
                title = {
                    Box(
                        modifier = Modifier.minimumInteractiveComponentSize()
                            .padding(horizontal = DiaryTheme.dimens.diaryHorizontalPadding),
                    ) {
                        Text(text = "대표 태그")
                    }
                },
                tag = { uiState ->
                    MemoTagChip(
                        uiState = uiState,
                        onClick = {
                            if (uiState != null) {
                                if (uiState.isPrimary) {
                                    onRemovePrimaryTag()
                                } else {
                                    onSelectPrimaryTag(uiState.tag.id)
                                }
                            }
                        },
                    )
                },
                modifier = Modifier.weight(1F),
            )
            TagCard(
                pagingItems = selectMemoTagPagingItems,
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(modifier = Modifier.padding(horizontal = DiaryTheme.dimens.diaryHorizontalPadding)) {
                            Text(text = "태그")
                        }
                        IconButton(onClick = onTagAdd) {
                            AddIcon()
                        }
                    }
                },
                tag = { uiState ->
                    TagChip(
                        isSelected = uiState?.isSelected ?: false,
                        title = uiState?.tag?.detail?.title.orEmpty(),
                        onClick = {
                            if (uiState != null) {
                                if (uiState.isSelected) {
                                    onUnselectMemoTag(uiState.tag.id)
                                } else {
                                    onSelectMemoTag(uiState.tag.id)
                                }
                            }
                        },
                        modifier = if (uiState == null) {
                            Modifier.width(80.dp)
                        } else {
                            Modifier
                        },
                    )
                },
                modifier = Modifier.weight(1F),
            )
        }
    }
}

@Composable
private fun TopBar(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = "메모 태그") },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                NavigateUpIcon()
            }
        },
    )
}

@Composable
private fun MemoTagScaffoldEffect(
    state: MemoTagScaffoldState,
    effectProvider: () -> MemoTagEffect,
    onEffect: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val effect = effectProvider()

    LaunchedEffect(state, effect) {
        when (effect) {
            MemoTagEffect.None -> Unit

            MemoTagEffect.UnknownError -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("네트워크 상태를 확인하세요") }
                onEffect()
            }
        }
    }
}

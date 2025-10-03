package io.github.taetae98coding.diary.feature.buddy.group.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.withResumed
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.taetae98coding.diary.compose.core.icon.ClearIcon
import io.github.taetae98coding.diary.compose.core.image.ProfileImage
import io.github.taetae98coding.diary.core.entity.buddy.Buddy
import io.github.taetae98coding.diary.library.paging.compose.isEmptyVisible
import io.github.taetae98coding.diary.library.paging.compose.isError
import io.github.taetae98coding.diary.library.paging.compose.isLoadingVisible

@Composable
internal fun BuddySearchBottomSheet(
    state: BuddySearchBottomSheetState,
    stateHolder: BuddySearchStateHolder,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagingItems = stateHolder.buddySearchPagingData.collectAsLazyPagingItems()

    BuddySearchBottomSheet(
        onDismissRequest = onDismissRequest,
        state = state,
        pagingItems = pagingItems,
        onSearch = stateHolder::search,
        onSelectBuddy = stateHolder::selectBuddy,
        onUnselectBuddy = stateHolder::unselectBuddy,
        modifier = modifier,
    )
}

@Composable
internal fun BuddySearchBottomSheet(
    onDismissRequest: () -> Unit,
    state: BuddySearchBottomSheetState,
    pagingItems: LazyPagingItems<BuddySearchUiState>,
    onSearch: (String) -> Unit,
    onSelectBuddy: (Buddy) -> Unit,
    onUnselectBuddy: (Buddy) -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = state.sheetState,
    ) {
        SearchBar(
            state = state,
            onSearch = onSearch,
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
        )

        BuddyColumn(
            pagingItems = pagingItems,
            onSelectBuddy = onSelectBuddy,
            onUnselectBuddy = onUnselectBuddy,
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.5F),
        )
    }

    SearchFocusEffect(focusRequester = state.textFieldFocusRequester)
}

@Composable
private fun SearchBar(
    state: BuddySearchBottomSheetState,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchBar(
        state = state.searchBarState,
        inputField = {
            SearchBarDefaults.InputField(
                textFieldState = state.textFieldState,
                searchBarState = state.searchBarState,
                onSearch = onSearch,
                modifier = Modifier.focusRequester(state.textFieldFocusRequester),
                placeholder = { Text(text = "diary@diary.com") },
                trailingIcon = {
                    val isVisible by remember(state) { derivedStateOf { state.textFieldState.text.isNotBlank() } }

                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut(),
                    ) {
                        IconButton(onClick = state.textFieldState::clearText) {
                            ClearIcon()
                        }
                    }
                },
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun BuddyColumn(
    pagingItems: LazyPagingItems<BuddySearchUiState>,
    onSelectBuddy: (Buddy) -> Unit,
    onUnselectBuddy: (Buddy) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        if (pagingItems.isLoadingVisible()) {
            item {
                Box(
                    modifier = Modifier.fillParentMaxSize()
                        .animateItem(),
                    contentAlignment = Alignment.Center,
                ) {
                    ContainedLoadingIndicator()
                }
            }
        } else if (pagingItems.isError()) {
            item {
                Column(
                    modifier = Modifier.fillParentMaxSize()
                        .animateItem(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = "네트워크 상태를 확인하세요")
                    Button(onClick = pagingItems::retry) {
                        Text(text = "다시 시도")
                    }
                }
            }
        } else if (pagingItems.isEmptyVisible()) {
            item {
                Box(
                    modifier = Modifier.fillParentMaxSize()
                        .animateItem(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = "버디가 없습니다")
                }
            }
        } else {
            items(count = pagingItems.itemCount) {
                val uiState = pagingItems[it]

                ListItem(
                    headlineContent = { Text(text = uiState?.buddy?.email.orEmpty()) },
                    modifier = Modifier.toggleable(
                        value = uiState?.isChecked ?: false,
                        onValueChange = { isChecked ->
                            uiState?.buddy?.let { buddy ->
                                if (isChecked) {
                                    onSelectBuddy(buddy)
                                } else {
                                    onUnselectBuddy(buddy)
                                }
                            }
                        },
                    ),
                    leadingContent = {
                        ProfileImage(
                            model = uiState?.buddy?.profileImage,
                            modifier = Modifier.size(48.dp),
                        )
                    },
                    trailingContent = {
                        if (uiState != null) {
                            Checkbox(
                                checked = uiState.isChecked,
                                onCheckedChange = null,
                            )
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun SearchFocusEffect(
    focusRequester: FocusRequester,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(
        lifecycleOwner,
        focusRequester,
    ) {
        lifecycleOwner.withResumed {
            focusRequester.requestFocus()
        }
    }
}

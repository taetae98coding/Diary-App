package io.github.taetae98coding.diary.feature.buddy.group.list

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester

internal class BuddyGroupListScreenState(
    val hostState: SnackbarHostState,
    val focusRequester: FocusRequester,
    val lazyListState: LazyListState,
)

@Composable
internal fun rememberBuddyGroupListScreenState(): BuddyGroupListScreenState {
    val hostState = remember { SnackbarHostState() }
    val focusRequester = remember { FocusRequester() }
    val lazyListState = rememberLazyListState()

    return remember(
        hostState,
        focusRequester,
        lazyListState,
    ) {
        BuddyGroupListScreenState(
            hostState = hostState,
            lazyListState = lazyListState,
            focusRequester = focusRequester,
        )
    }
}

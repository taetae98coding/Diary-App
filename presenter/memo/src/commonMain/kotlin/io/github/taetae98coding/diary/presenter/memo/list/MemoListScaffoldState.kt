package io.github.taetae98coding.diary.presenter.memo.list

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester

public data class MemoListScaffoldState(
    internal val hostState: SnackbarHostState,
    internal val focusRequester: FocusRequester,
    val lazyListState: LazyListState,
)

@Composable
public fun rememberMemoListScaffoldState(): MemoListScaffoldState {
    val hostState = remember { SnackbarHostState() }
    val focusRequester = remember { FocusRequester() }
    val lazyListState = rememberLazyListState()

    return remember(
        hostState,
        focusRequester,
        lazyListState,
    ) {
        MemoListScaffoldState(
            hostState = hostState,
            focusRequester = focusRequester,
            lazyListState = lazyListState,
        )
    }
}

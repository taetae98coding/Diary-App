package io.github.taetae98coding.diary.presenter.tag.list

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester

public data class TagListScaffoldState(
    internal val hostState: SnackbarHostState,
    internal val focusRequester: FocusRequester,
    val lazyGridState: LazyGridState,
)

@Composable
public fun rememberTagListScaffoldState(): TagListScaffoldState {
    val hostState = remember { SnackbarHostState() }
    val focusRequester = remember { FocusRequester() }
    val lazyGridState = rememberLazyGridState()

    return remember(
        hostState,
        focusRequester,
        lazyGridState,
    ) {
        TagListScaffoldState(
            hostState = hostState,
            focusRequester = focusRequester,
            lazyGridState = lazyGridState,
        )
    }
}

package io.github.taetae98coding.diary.presenter.memo.tag

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

internal data class MemoTagScaffoldState(
    val hostState: SnackbarHostState,
)

@Composable
internal fun rememberMemoTagScaffoldState(): MemoTagScaffoldState {
    val hostState = remember { SnackbarHostState() }

    return remember(hostState) {
        MemoTagScaffoldState(
            hostState = hostState,
        )
    }
}

package io.github.taetae98coding.diary.presenter.memo.finish

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

internal data class FinishedMemoScaffoldState(
    val hostState: SnackbarHostState,

)

@Composable
internal fun rememberFinishedMemoScaffoldState(): FinishedMemoScaffoldState {
    val hostState = remember { SnackbarHostState() }

    return remember(
        hostState,
    ) {
        FinishedMemoScaffoldState(
            hostState = hostState,
        )
    }
}

package io.github.taetae98coding.diary.presenter.tag.finish

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

internal data class FinishedTagScaffoldState(
    val hostState: SnackbarHostState,

)

@Composable
internal fun rememberFinishedTagScaffoldState(): FinishedTagScaffoldState {
    val hostState = remember { SnackbarHostState() }

    return remember(
        hostState,
    ) {
        FinishedTagScaffoldState(
            hostState = hostState,
        )
    }
}

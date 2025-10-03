package io.github.taetae98coding.diary.presenter.tag.add

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.taetae98coding.diary.presenter.tag.TagDetailState
import io.github.taetae98coding.diary.presenter.tag.rememberTagDetailState

internal class TagAddScaffoldState(
    val hostState: SnackbarHostState,
    val detailState: TagDetailState,
)

@Composable
internal fun rememberTagAddScaffoldState(): TagAddScaffoldState {
    val hostState = remember { SnackbarHostState() }
    val detailState = rememberTagDetailState()

    return remember(
        hostState,
        detailState,
    ) {
        TagAddScaffoldState(
            hostState = hostState,
            detailState = detailState,
        )
    }
}

package io.github.taetae98coding.diary.presenter.tag.detail

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.presenter.tag.TagDetailState
import io.github.taetae98coding.diary.presenter.tag.rememberTagDetailState

internal data class TagDetailScaffoldState(
    val hostState: SnackbarHostState,
    val detailState: TagDetailState,
)

@Composable
internal fun rememberTagDetailScaffoldState(
    detailProvider: () -> TagDetail?,
): TagDetailScaffoldState {
    val hostState = remember { SnackbarHostState() }
    val detailState = rememberTagDetailState(detailProvider = detailProvider)

    return remember(
        hostState,
        detailState,
    ) {
        TagDetailScaffoldState(
            hostState = hostState,
            detailState = detailState,
        )
    }
}

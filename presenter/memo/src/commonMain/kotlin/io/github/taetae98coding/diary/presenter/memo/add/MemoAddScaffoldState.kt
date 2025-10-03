package io.github.taetae98coding.diary.presenter.memo.add

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.taetae98coding.diary.presenter.memo.MemoDetailState
import io.github.taetae98coding.diary.presenter.memo.rememberMemoDetailState
import kotlinx.datetime.LocalDateRange

internal data class MemoAddScaffoldState(
    val hostState: SnackbarHostState,
    val detailState: MemoDetailState,
)

@Composable
internal fun rememberMemoAddScaffoldState(
    initialDateRange: LocalDateRange? = null,
): MemoAddScaffoldState {
    val hostState = remember { SnackbarHostState() }
    val detailState = rememberMemoDetailState(initialDateRange = initialDateRange)

    return remember(
        hostState,
        detailState,
    ) {
        MemoAddScaffoldState(
            hostState = hostState,
            detailState = detailState,
        )
    }
}

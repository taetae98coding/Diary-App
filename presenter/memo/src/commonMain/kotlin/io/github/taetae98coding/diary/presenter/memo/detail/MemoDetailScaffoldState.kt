package io.github.taetae98coding.diary.presenter.memo.detail

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.presenter.memo.MemoDetailState
import io.github.taetae98coding.diary.presenter.memo.rememberMemoDetailState

internal data class MemoDetailScaffoldState(
    val hostState: SnackbarHostState,
    val detailState: MemoDetailState,
)

@Composable
internal fun rememberMemoDetailScaffoldState(
    detailProvider: () -> MemoDetail?,
): MemoDetailScaffoldState {
    val hostState = remember { SnackbarHostState() }
    val detailState = rememberMemoDetailState(detailProvider = detailProvider)

    return remember(
        hostState,
        detailState,
    ) {
        MemoDetailScaffoldState(
            hostState = hostState,
            detailState = detailState,
        )
    }
}

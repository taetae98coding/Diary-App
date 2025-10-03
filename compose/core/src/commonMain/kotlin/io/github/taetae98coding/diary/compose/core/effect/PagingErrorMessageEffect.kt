package io.github.taetae98coding.diary.compose.core.effect

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.paging.compose.LazyPagingItems
import io.github.taetae98coding.diary.compose.core.snackbar.showSnackbarImmediate
import io.github.taetae98coding.diary.library.paging.compose.isError

@Composable
public fun <T : Any> PagingErrorMessageEffect(
    hostState: SnackbarHostState,
    pagingItems: LazyPagingItems<T>,
) {
    LaunchedEffect(
        hostState,
        pagingItems.isError(),
    ) {
        if (pagingItems.isError()) {
            hostState.showSnackbarImmediate("오프라인 상태입니다")
        }
    }
}

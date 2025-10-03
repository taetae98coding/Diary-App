package io.github.taetae98coding.diary.compose.core.effect

import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import io.github.taetae98coding.diary.library.paging.compose.isError
import io.github.taetae98coding.diary.library.paging.compose.isNotLoading

@Composable
public fun <T : Any> PagingReloadEffect(
    pagingItems: LazyPagingItems<T>,
) {
    PlatformRefreshLifecycleEffect(pagingItems) {
        if (pagingItems.isNotLoading()) {
            pagingItems.refresh()
        } else if (pagingItems.isError()) {
            pagingItems.retry()
        }
    }
}

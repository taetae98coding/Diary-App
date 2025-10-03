package io.github.taetae98coding.diary.library.paging.compose

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import io.github.taetae98coding.diary.library.paging.common.isLoading
import io.github.taetae98coding.diary.library.paging.common.isNotLoading

public fun LazyPagingItems<*>.isEmpty(): Boolean {
    return itemCount == 0
}

public fun LazyPagingItems<*>.isLoading(): Boolean {
    return loadState.isLoading()
}

public fun LazyPagingItems<*>.isNotLoading(): Boolean {
    return loadState.isNotLoading()
}

public fun LazyPagingItems<*>.isError(): Boolean {
    return loadState.hasError
}

public fun LazyPagingItems<*>.throwableOrNull(): Throwable? {
    return (loadState.refresh as? LoadState.Error)?.error
        ?: (loadState.append as? LoadState.Error)?.error
        ?: (loadState.prepend as? LoadState.Error)?.error
}

public fun LazyPagingItems<*>.isLoadingVisible(): Boolean {
    return isLoading() && isEmpty()
}

public fun LazyPagingItems<*>.isEmptyVisible(): Boolean {
    return !isLoading() && isEmpty()
}

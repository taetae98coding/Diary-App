package io.github.taetae98coding.diary.library.paging.common

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

public fun CombinedLoadStates.isLoading(): Boolean {
    return refresh is LoadState.Loading
}

public fun CombinedLoadStates.isNotLoading(): Boolean {
    return refresh is LoadState.NotLoading
}

public fun CombinedLoadStates.isError(): Boolean {
    return hasError
}

public fun CombinedLoadStates.throwableOrNull(): Throwable? {
    return (refresh as? LoadState.Error)?.error
        ?: (append as? LoadState.Error)?.error
        ?: (prepend as? LoadState.Error)?.error
}

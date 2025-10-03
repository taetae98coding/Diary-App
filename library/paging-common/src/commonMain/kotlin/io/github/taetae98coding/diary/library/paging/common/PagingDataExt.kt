package io.github.taetae98coding.diary.library.paging.common

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData

public fun <T : Any> PagingData.Companion.loading(
    data: List<T> = emptyList(),
): PagingData<T> {
    return PagingData.from(
        data = data,
        sourceLoadStates = LoadStates(
            refresh = LoadState.Loading,
            prepend = LoadState.NotLoading(false),
            append = LoadState.NotLoading(false),
        ),
    )
}

public fun <T : Any> PagingData.Companion.notLoading(
    data: List<T> = emptyList(),
): PagingData<T> {
    return PagingData.from(
        data = data,
        sourceLoadStates = LoadStates(
            refresh = LoadState.NotLoading(true),
            prepend = LoadState.NotLoading(true),
            append = LoadState.NotLoading(true),
        ),
    )
}

public fun <T : Any> PagingData.Companion.error(
    data: List<T> = emptyList(),
    error: Throwable = Exception(),
): PagingData<T> {
    return PagingData.from(
        data = data,
        sourceLoadStates = LoadStates(
            refresh = LoadState.Error(error),
            prepend = LoadState.NotLoading(true),
            append = LoadState.NotLoading(true),
        ),
    )
}

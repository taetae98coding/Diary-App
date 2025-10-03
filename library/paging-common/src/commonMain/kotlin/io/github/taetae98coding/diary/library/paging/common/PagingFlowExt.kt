package io.github.taetae98coding.diary.library.paging.common

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

public fun <T : Any, R : Any> Flow<PagingData<T>>.mapPagingLatest(
    transform: suspend (T) -> R,
): Flow<PagingData<R>> {
    return mapLatest { it.map(transform) }
}

public fun <T : Any> Flow<PagingData<T>>.filterPagingLatest(
    transform: suspend (T) -> Boolean,
): Flow<PagingData<T>> {
    return mapLatest { it.filter(transform) }
}

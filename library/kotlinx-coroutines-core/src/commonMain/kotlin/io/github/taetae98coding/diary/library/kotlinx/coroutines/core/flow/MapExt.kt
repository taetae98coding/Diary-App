package io.github.taetae98coding.diary.library.kotlinx.coroutines.core.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

public inline fun <T, R> Flow<Collection<T>>.mapCollectionLatest(
    crossinline transform: suspend (T) -> R,
): Flow<List<R>> {
    return mapLatest { collection ->
        collection.map {
            transform(it)
        }
    }
}

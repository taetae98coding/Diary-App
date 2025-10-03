package io.github.taetae98coding.diary.library.kotlinx.coroutines.core.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

public inline fun <reified T, R> Iterable<Flow<T>>.combine(
    crossinline transform: suspend (Array<T>) -> R,
): Flow<R> {
    return combine(this, transform)
}

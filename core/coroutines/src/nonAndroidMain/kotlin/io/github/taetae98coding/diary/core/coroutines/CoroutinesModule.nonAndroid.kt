package io.github.taetae98coding.diary.core.coroutines

import io.github.taetae98coding.diary.core.coroutines.ext.MainImmediateScope
import kotlinx.coroutines.CoroutineScope

internal val applicationScope by lazy {
    MainImmediateScope()
}

internal actual fun applicationScope(): CoroutineScope {
    return applicationScope
}

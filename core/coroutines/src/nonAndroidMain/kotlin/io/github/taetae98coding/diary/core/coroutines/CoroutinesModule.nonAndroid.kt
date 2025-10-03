package io.github.taetae98coding.diary.core.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

internal val applicationScope by lazy {
    CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
}

internal actual fun applicationScope(): CoroutineScope {
    return applicationScope
}

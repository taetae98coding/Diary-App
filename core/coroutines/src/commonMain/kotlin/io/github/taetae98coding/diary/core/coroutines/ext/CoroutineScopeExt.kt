package io.github.taetae98coding.diary.core.coroutines.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Suppress("FunctionName")
public fun MainImmediateScope(): CoroutineScope {
    return CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
}

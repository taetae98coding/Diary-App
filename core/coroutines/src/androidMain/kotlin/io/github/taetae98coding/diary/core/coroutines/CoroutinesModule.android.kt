package io.github.taetae98coding.diary.core.coroutines

import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope

internal actual fun applicationScope(): CoroutineScope {
    return ProcessLifecycleOwner.get().lifecycleScope
}

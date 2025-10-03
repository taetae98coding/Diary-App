package io.github.taetae98coding.diary.library.lifecycle.common

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

public fun LifecycleOwner.ifResumed(
    action: () -> Unit,
) {
    if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
        action()
    }
}

package io.github.taetae98coding.diary.compose.core.effect

import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
public fun PlatformRefreshLifecycleEffect(
    vararg keys: Any?,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    effects: LifecycleOwner.() -> Unit,
) {
    LifecycleStartEffect(
        keys = keys,
        lifecycleOwner = lifecycleOwner,
    ) {
        effects()
        onStopOrDispose {}
    }
}

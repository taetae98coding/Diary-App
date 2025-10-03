package io.github.taetae98coding.diary.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.navigation3.scene.Scene
import androidx.navigation3.ui.defaultPopTransitionSpec
import androidx.navigation3.ui.defaultPredictivePopTransitionSpec
import androidx.navigation3.ui.defaultTransitionSpec
import androidx.navigationevent.NavigationEvent

internal actual fun <T : Any> platformTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform {
    return defaultTransitionSpec()
}

internal actual fun <T : Any> platformPopTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform {
    return defaultPopTransitionSpec()
}

internal actual fun <T : Any> platformPredictivePopTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.(@NavigationEvent.SwipeEdge Int) -> ContentTransform {
    return defaultPredictivePopTransitionSpec()
}

package io.github.taetae98coding.diary.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.navigation3.scene.Scene
import androidx.navigationevent.NavigationEvent

internal expect fun <T : Any> platformTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform
internal expect fun <T : Any> platformPopTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform
internal expect fun <T : Any> platformPredictivePopTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.(
    @NavigationEvent.SwipeEdge Int,
) -> ContentTransform

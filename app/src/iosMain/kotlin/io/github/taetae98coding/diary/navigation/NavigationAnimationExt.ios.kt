package io.github.taetae98coding.diary.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.navigation3.scene.Scene
import androidx.navigation3.ui.defaultPopTransitionSpec
import androidx.navigation3.ui.defaultPredictivePopTransitionSpec
import androidx.navigation3.ui.defaultTransitionSpec
import androidx.navigationevent.NavigationEvent
import io.github.taetae98coding.diary.core.navigation.buddy.BuddyGroupListNavKey
import io.github.taetae98coding.diary.core.navigation.calendar.CalendarNavKey
import io.github.taetae98coding.diary.core.navigation.memo.MemoListNavKey
import io.github.taetae98coding.diary.core.navigation.more.MoreNavKey
import io.github.taetae98coding.diary.core.navigation.tag.TagListNavKey

private const val DEFAULT_TRANSITION_DURATION_MILLISECOND = 700

private fun Scene<*>.isTopLevel(): Boolean {
    val topLevelNavKey = listOfNotNull(
        MemoListNavKey::class.simpleName,
        TagListNavKey::class.simpleName,
        CalendarNavKey::class.simpleName,
        BuddyGroupListNavKey::class.simpleName,
        MoreNavKey::class.simpleName,
    )

    return topLevelNavKey.contains(key)
}

internal actual fun <T : Any> platformTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform {
    return {
        if (initialState.isTopLevel() && targetState.isTopLevel()) {
            ContentTransform(
                fadeIn(animationSpec = tween(DEFAULT_TRANSITION_DURATION_MILLISECOND)),
                fadeOut(animationSpec = tween(DEFAULT_TRANSITION_DURATION_MILLISECOND)),
            )
        } else {
            defaultTransitionSpec<T>().invoke(this)
        }
    }
}

internal actual fun <T : Any> platformPopTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform {
    return {
        if (initialState.isTopLevel() && targetState.isTopLevel()) {
            ContentTransform(
                fadeIn(animationSpec = tween(DEFAULT_TRANSITION_DURATION_MILLISECOND)),
                fadeOut(animationSpec = tween(DEFAULT_TRANSITION_DURATION_MILLISECOND)),
            )
        } else {
            defaultPopTransitionSpec<T>().invoke(this)
        }
    }
}

internal actual fun <T : Any> platformPredictivePopTransitionSpec(): AnimatedContentTransitionScope<Scene<T>>.(@NavigationEvent.SwipeEdge Int) -> ContentTransform {
    return {
        if (initialState.isTopLevel() && targetState.isTopLevel()) {
            ContentTransform(
                fadeIn(
                    spring(
                        dampingRatio = 1.0F,
                        stiffness = 1600.0F,
                    ),
                ),
                scaleOut(targetScale = 0.7F),
            )
        } else {
            defaultPredictivePopTransitionSpec<T>().invoke(this, it)
        }
    }
}

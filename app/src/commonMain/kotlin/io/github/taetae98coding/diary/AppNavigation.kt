package io.github.taetae98coding.diary

import androidx.compose.foundation.focusable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import io.github.taetae98coding.diary.feature.buddy.group.buddyGroupEntryProvider
import io.github.taetae98coding.diary.feature.calendar.calendarEntryProvider
import io.github.taetae98coding.diary.feature.login.loginEntryProvider
import io.github.taetae98coding.diary.feature.memo.memoEntryProvider
import io.github.taetae98coding.diary.feature.more.moreEntryProvider
import io.github.taetae98coding.diary.feature.tag.tagEntryProvider
import io.github.taetae98coding.diary.library.compose.ui.isPlatformMetaPressed
import io.github.taetae98coding.diary.library.compose.ui.onPreviewKeyUp
import io.github.taetae98coding.diary.navigation.TopLevelDestination
import io.github.taetae98coding.diary.navigation.platformPopTransitionSpec
import io.github.taetae98coding.diary.navigation.platformPredictivePopTransitionSpec
import io.github.taetae98coding.diary.navigation.platformTransitionSpec

@Composable
internal fun AppNavigation(
    state: AppState,
    modifier: Modifier = Modifier,
) {
    NavDisplay(
        backStack = state.backStack,
        modifier = modifier.navigationShortcut(state),
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        sceneStrategy = remember { DialogSceneStrategy() },
        entryProvider = entryProvider {
            memoEntryProvider(
                backStack = state.backStack,
                scrollState = state.memoScrollState,
            )
            tagEntryProvider(
                backStack = state.backStack,
                scrollState = state.tagScrollState,
            )
            calendarEntryProvider(
                backStack = state.backStack,
                scrollState = state.calendarScrollState,
            )
            buddyGroupEntryProvider(
                backStack = state.backStack,
                scrollState = state.buddyGroupScrollState,
            )
            moreEntryProvider(backStack = state.backStack)
            loginEntryProvider(backStack = state.backStack)
        },
        transitionSpec = platformTransitionSpec(),
        popTransitionSpec = platformPopTransitionSpec(),
        predictivePopTransitionSpec = platformPredictivePopTransitionSpec(),
    )
}

private fun Modifier.navigationShortcut(
    state: AppState,
): Modifier {
    return focusable()
        .onPreviewKeyUp { keyEvent ->
            if (!state.isNavigationVisible) {
                return@onPreviewKeyUp false
            }

            when {
                keyEvent.isPlatformMetaPressed() && keyEvent.key == Key.One -> {
                    if (state.currentTopLevelDestination == TopLevelDestination.Memo) {
                        state.memoScrollState.requestScrollToTop()
                    } else {
                        state.navigate(TopLevelDestination.Memo)
                    }
                    true
                }

                keyEvent.isPlatformMetaPressed() && keyEvent.key == Key.Two -> {
                    if (state.currentTopLevelDestination == TopLevelDestination.Tag) {
                        state.tagScrollState.requestScrollToTop()
                    } else {
                        state.navigate(TopLevelDestination.Tag)
                    }
                    true
                }

                keyEvent.isPlatformMetaPressed() && keyEvent.key == Key.Three -> {
                    if (state.currentTopLevelDestination == TopLevelDestination.Calendar) {
                        state.calendarScrollState.requestScrollToTop()
                    } else {
                        state.navigate(TopLevelDestination.Calendar)
                    }
                    true
                }

                keyEvent.isPlatformMetaPressed() && keyEvent.key == Key.Four -> {
                    if (state.currentTopLevelDestination == TopLevelDestination.BuddyGroup) {
                        state.buddyGroupScrollState.requestScrollToTop()
                    } else {
                        state.navigate(TopLevelDestination.BuddyGroup)
                    }
                    true
                }

                keyEvent.isPlatformMetaPressed() && keyEvent.key == Key.Five -> {
                    state.navigate(TopLevelDestination.More)
                    true
                }

                else -> false
            }
        }
}

package io.github.taetae98coding.diary

import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldState
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import io.github.taetae98coding.diary.core.navigation.AppNavigationNavKey
import io.github.taetae98coding.diary.core.navigation.calendar.CalendarNavKey
import io.github.taetae98coding.diary.feature.buddy.group.BuddyGroupScrollState
import io.github.taetae98coding.diary.feature.buddy.group.rememberBuddyGroupScrollState
import io.github.taetae98coding.diary.feature.calendar.CalendarScrollState
import io.github.taetae98coding.diary.feature.calendar.rememberCalendarScrollState
import io.github.taetae98coding.diary.feature.dday.DdayScrollState
import io.github.taetae98coding.diary.feature.dday.rememberDdayScrollState
import io.github.taetae98coding.diary.feature.memo.MemoScrollState
import io.github.taetae98coding.diary.feature.memo.rememberMemoScrollState
import io.github.taetae98coding.diary.feature.tag.TagScrollState
import io.github.taetae98coding.diary.feature.tag.rememberTagScrollState
import io.github.taetae98coding.diary.library.navigation3.compat.rememberCompatNavBackStack
import io.github.taetae98coding.diary.navigation.TopLevelDestination

internal class AppState(
    val backStack: NavBackStack<NavKey>,
    val scaffoldState: NavigationSuiteScaffoldState,
    val memoScrollState: MemoScrollState,
    val tagScrollState: TagScrollState,
    val calendarScrollState: CalendarScrollState,
    val buddyGroupScrollState: BuddyGroupScrollState,
    val ddayScrollState: DdayScrollState,
) {
    val topLevelDestination = listOf(
        TopLevelDestination.Memo,
        TopLevelDestination.Tag,
        TopLevelDestination.Calendar,
        TopLevelDestination.BuddyGroup,
        TopLevelDestination.More,
        TopLevelDestination.Dday,
    )

    val currentTopLevelDestination by derivedStateOf {
        val topLevelNavKeyList = topLevelDestination.map(TopLevelDestination::navKey)
        val lastTopLevelNavKey = backStack.lastOrNull { it in topLevelNavKeyList }

        topLevelDestination.find { it.navKey == lastTopLevelNavKey }
    }

    val isNavigationVisible by derivedStateOf {
        val isTopLevelNavKey = backStack.lastOrNull() in topLevelDestination.map(TopLevelDestination::navKey)
        val isAppNavigationNavKey = backStack.lastOrNull() is AppNavigationNavKey

        isTopLevelNavKey || isAppNavigationNavKey
    }

    fun navigate(destination: TopLevelDestination) {
        backStack.clear()
        backStack.addAll(listOf(CalendarNavKey, destination.navKey).distinct())
    }
}

@Composable
internal fun rememberAppState(): AppState {
    val backStack = rememberCompatNavBackStack(CalendarNavKey)
    val scaffoldState = rememberNavigationSuiteScaffoldState()
    val memoScrollState = rememberMemoScrollState()
    val tagScrollState = rememberTagScrollState()
    val calendarScrollState = rememberCalendarScrollState()
    val buddyGroupScrollState = rememberBuddyGroupScrollState()
    val ddayScrollState = rememberDdayScrollState()

    return remember(
        backStack,
        scaffoldState,
        memoScrollState,
        tagScrollState,
        calendarScrollState,
        buddyGroupScrollState,
        ddayScrollState,
    ) {
        AppState(
            backStack = backStack,
            scaffoldState = scaffoldState,
            memoScrollState = memoScrollState,
            tagScrollState = tagScrollState,
            calendarScrollState = calendarScrollState,
            buddyGroupScrollState = buddyGroupScrollState,
            ddayScrollState = ddayScrollState,
        )
    }
}

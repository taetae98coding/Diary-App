package io.github.taetae98coding.diary

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import io.github.taetae98coding.diary.navigation.TopLevelDestination

@Composable
internal fun AppScaffold(
    state: AppState,
    modifier: Modifier = Modifier,
) {
    val layoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo())

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            val destination = if (layoutType == NavigationSuiteType.NavigationBar) {
                state.topLevelDestination.take(5)
            } else {
                state.topLevelDestination
            }

            destination.forEach { destination ->
                val isSelected = state.currentTopLevelDestination == destination

                item(
                    selected = state.currentTopLevelDestination == destination,
                    onClick = {
                        if (isSelected) {
                            when (destination) {
                                TopLevelDestination.Memo -> state.memoScrollState.requestScrollToTop()
                                TopLevelDestination.Tag -> state.tagScrollState.requestScrollToTop()
                                TopLevelDestination.Calendar -> state.calendarScrollState.requestScrollToTop()
                                TopLevelDestination.BuddyGroup -> state.buddyGroupScrollState.requestScrollToTop()
                                TopLevelDestination.Dday -> state.ddayScrollState.requestScrollToTop()
                                else -> Unit
                            }
                        } else {
                            state.navigate(destination)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = destination.imageVector,
                            contentDescription = destination.title,
                        )
                    },
                    label = { Text(text = destination.title) },
                    alwaysShowLabel = false,
                )
            }
        },
        modifier = modifier,
        layoutType = layoutType,
        state = state.scaffoldState,
    ) {
        AppNavigation(
            state = state,
        )
    }

    NavigationVisibleEffect(
        appState = state,
    )
}

@Composable
private fun NavigationVisibleEffect(
    appState: AppState,
) {
    LaunchedEffect(appState.isNavigationVisible) {
        if (appState.isNavigationVisible) {
            appState.scaffoldState.show()
        } else {
            appState.scaffoldState.hide()
        }
    }
}

package io.github.taetae98coding.diary.feature.dday

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import io.github.taetae98coding.diary.core.navigation.dday.DdayAddNavKey
import io.github.taetae98coding.diary.core.navigation.dday.DdayListNavKey
import io.github.taetae98coding.diary.feature.dday.add.DdayAddScreen
import io.github.taetae98coding.diary.feature.dday.list.DdayListScreen

public fun EntryProviderScope<NavKey>.ddayEntryProvider(
    backStack: NavBackStack<NavKey>,
    scrollState: DdayScrollState,
) {
    entry<DdayListNavKey> {
        DdayListScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToDdayAdd = { backStack.add(DdayAddNavKey) },
        )
    }

    entry<DdayAddNavKey> {
        DdayAddScreen(
            onNavigateUp = backStack::removeLastOrNull,
        )
    }
}

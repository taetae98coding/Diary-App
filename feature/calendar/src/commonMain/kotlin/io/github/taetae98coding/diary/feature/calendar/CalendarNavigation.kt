package io.github.taetae98coding.diary.feature.calendar

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import io.github.taetae98coding.diary.core.navigation.calendar.CalendarMemoFilterNavKey
import io.github.taetae98coding.diary.core.navigation.calendar.CalendarNavKey
import io.github.taetae98coding.diary.core.navigation.memo.MemoAddNavKey
import io.github.taetae98coding.diary.core.navigation.memo.MemoDetailNavKey
import io.github.taetae98coding.diary.feature.calendar.filter.CalendarMemoFilterDialog

public fun EntryProviderBuilder<NavKey>.calendarEntryProvider(
    backStack: NavBackStack<NavKey>,
    scrollState: CalendarScrollState,
) {
    entry<CalendarNavKey> { navKey ->
        CalendarScreen(
            scrollState = scrollState,
            navigateToCalendarMemoFilter = { backStack.add(CalendarMemoFilterNavKey) },
            navigateToMemoAdd = { backStack.add(MemoAddNavKey(start = it.start, endInclusive = it.endInclusive)) },
            navigateToMemoDetail = { backStack.add(MemoDetailNavKey(it)) },
        )
    }

    entry<CalendarMemoFilterNavKey> {
        CalendarMemoFilterDialog(
            navigateUp = backStack::removeLastOrNull,
        )
    }
}

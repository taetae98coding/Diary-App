package io.github.taetae98coding.diary.feature.calendar

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import io.github.taetae98coding.diary.core.navigation.calendar.CalendarFilterNavKey
import io.github.taetae98coding.diary.core.navigation.calendar.CalendarNavKey
import io.github.taetae98coding.diary.core.navigation.memo.MemoAddNavKey
import io.github.taetae98coding.diary.core.navigation.memo.MemoDetailNavKey
import io.github.taetae98coding.diary.core.navigation.tag.TagAddNavKey
import io.github.taetae98coding.diary.feature.calendar.filter.CalendarFilterDialog

public fun EntryProviderBuilder<NavKey>.calendarEntryProvider(
    backStack: NavBackStack<NavKey>,
    scrollState: CalendarScrollState,
) {
    entry<CalendarNavKey> { navKey ->
        CalendarScreen(
            scrollState = scrollState,
            navigateToCalendarFilter = { backStack.add(CalendarFilterNavKey) },
            navigateToMemoAdd = { backStack.add(MemoAddNavKey(start = it.start, endInclusive = it.endInclusive)) },
            navigateToMemoDetail = { memoId -> backStack.add(MemoDetailNavKey(memoId)) },
        )
    }

    entry<CalendarFilterNavKey>(
        // TODO dialog
//        metadata = DialogSceneStrategy.dialog(),
    ) {
        CalendarFilterDialog(
            navigateUp = backStack::removeLastOrNull,
            navigateToTagAdd = { backStack.add(TagAddNavKey) },
        )
    }
}

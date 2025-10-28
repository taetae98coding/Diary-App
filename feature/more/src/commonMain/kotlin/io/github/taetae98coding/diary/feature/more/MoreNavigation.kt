package io.github.taetae98coding.diary.feature.more

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import io.github.taetae98coding.diary.core.navigation.dday.DdayListNavKey
import io.github.taetae98coding.diary.core.navigation.login.LoginNavKey
import io.github.taetae98coding.diary.core.navigation.memo.MemoDetailNavKey
import io.github.taetae98coding.diary.core.navigation.more.FinishedMemoNavKey
import io.github.taetae98coding.diary.core.navigation.more.FinishedTagNavKey
import io.github.taetae98coding.diary.core.navigation.more.GoldHolidayNavKey
import io.github.taetae98coding.diary.core.navigation.more.MoreNavKey
import io.github.taetae98coding.diary.core.navigation.tag.TagDetailNavKey
import io.github.taetae98coding.diary.core.navigation.tag.TagMemoNavKey
import io.github.taetae98coding.diary.feature.more.finish.FinishedMemoScreen
import io.github.taetae98coding.diary.feature.more.finish.FinishedTagScreen
import io.github.taetae98coding.diary.feature.more.holiday.GoldHolidayScreen
import io.github.taetae98coding.diary.feature.more.home.MoreScreen

public fun EntryProviderScope<NavKey>.moreEntryProvider(
    backStack: NavBackStack<NavKey>,
) {
    entry<MoreNavKey> {
        MoreScreen(
            navigateToLogin = { backStack.add(LoginNavKey) },
            navigateToFinishMemo = { backStack.add(FinishedMemoNavKey) },
            navigateToFinishTag = { backStack.add(FinishedTagNavKey) },
            navigateToGoldHoliday = { backStack.add(GoldHolidayNavKey) },
            navigateToDday = { backStack.add(DdayListNavKey) },
        )
    }

    entry<GoldHolidayNavKey> {
        GoldHolidayScreen(
            onNavigateUp = backStack::removeLastOrNull,
        )
    }

    entry<FinishedMemoNavKey> {
        FinishedMemoScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToMemoDetail = { memoId -> backStack.add(MemoDetailNavKey(memoId)) },
        )
    }

    entry<FinishedTagNavKey> {
        FinishedTagScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToTagDetail = { tagId -> backStack.add(TagDetailNavKey(tagId)) },
            navigateToTagMemo = { tagId -> backStack.add(TagMemoNavKey(tagId)) },
        )
    }
}

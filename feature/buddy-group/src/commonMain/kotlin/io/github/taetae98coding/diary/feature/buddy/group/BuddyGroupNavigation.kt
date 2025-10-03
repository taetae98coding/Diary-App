package io.github.taetae98coding.diary.feature.buddy.group

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.DialogSceneStrategy
import io.github.taetae98coding.diary.core.navigation.buddy.BuddyGroupAddNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.BuddyGroupCalendarNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.BuddyGroupDetailNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.BuddyGroupListNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.calendar.BuddyGroupCalendarFilterNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.memo.BuddyGroupFinishedMemoNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.memo.BuddyGroupMemoAddNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.memo.BuddyGroupMemoDetailNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.memo.BuddyGroupMemoListFilterNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.memo.BuddyGroupMemoListNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.tag.BuddyGroupFinishedTagNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.tag.BuddyGroupTagAddNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.tag.BuddyGroupTagDetailNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.tag.BuddyGroupTagListNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.tag.BuddyGroupTagMemoNavKey
import io.github.taetae98coding.diary.core.navigation.login.LoginNavKey
import io.github.taetae98coding.diary.core.navigation.parameter.BuddyGroupId
import io.github.taetae98coding.diary.core.navigation.parameter.MemoId
import io.github.taetae98coding.diary.core.navigation.parameter.PrimaryTag
import io.github.taetae98coding.diary.core.navigation.parameter.TagId
import io.github.taetae98coding.diary.feature.buddy.group.add.BuddyGroupAddScreen
import io.github.taetae98coding.diary.feature.buddy.group.calendar.BuddyGroupCalendarScreen
import io.github.taetae98coding.diary.feature.buddy.group.calendar.filter.BuddyGroupCalendarFilterDialog
import io.github.taetae98coding.diary.feature.buddy.group.detail.BuddyGroupDetailScreen
import io.github.taetae98coding.diary.feature.buddy.group.list.BuddyGroupListScreen
import io.github.taetae98coding.diary.feature.buddy.group.memo.add.BuddyGroupMemoAddScreen
import io.github.taetae98coding.diary.feature.buddy.group.memo.detail.BuddyGroupMemoDetailScreen
import io.github.taetae98coding.diary.feature.buddy.group.memo.filter.BuddyGroupMemoListFilterDialog
import io.github.taetae98coding.diary.feature.buddy.group.memo.finish.BuddyGroupFinishMemoScreen
import io.github.taetae98coding.diary.feature.buddy.group.memo.list.BuddyGroupMemoListScreen
import io.github.taetae98coding.diary.feature.buddy.group.tag.add.BuddyGroupTagAddScreen
import io.github.taetae98coding.diary.feature.buddy.group.tag.detail.BuddyGroupTagDetailScreen
import io.github.taetae98coding.diary.feature.buddy.group.tag.finish.BuddyGroupFinishedTagScreen
import io.github.taetae98coding.diary.feature.buddy.group.tag.list.BuddyGroupTagListScreen
import io.github.taetae98coding.diary.feature.buddy.group.tag.memo.BuddyGroupTagMemoScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

public fun EntryProviderScope<NavKey>.buddyGroupEntryProvider(
    backStack: NavBackStack<NavKey>,
    scrollState: BuddyGroupScrollState,
) {
    entry<BuddyGroupListNavKey> { navKey ->
        BuddyGroupListScreen(
            scrollState = scrollState,
            navigateToLogin = { backStack.add(LoginNavKey) },
            navigateToBuddyGroupAdd = { backStack.add(BuddyGroupAddNavKey) },
            navigateToBuddyGroupDetail = { buddyGroupId -> backStack.add(BuddyGroupDetailNavKey(buddyGroupId)) },
            navigateToBuddyGroupMemoList = { buddyGroupId -> backStack.add(BuddyGroupMemoListNavKey(buddyGroupId)) },
            navigateToBuddyGroupTagList = { buddyGroupId -> backStack.add(BuddyGroupTagListNavKey(buddyGroupId)) },
            navigateToBuddyGroupCalendar = { buddyGroupId -> backStack.add(BuddyGroupCalendarNavKey(buddyGroupId)) },
        )
    }

    entry<BuddyGroupAddNavKey> {
        BuddyGroupAddScreen(
            navigateUp = backStack::removeLastOrNull,
        )
    }

    entry<BuddyGroupDetailNavKey> { navKey ->
        BuddyGroupDetailScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupMemoList = { backStack.add(BuddyGroupMemoListNavKey(navKey.buddyGroupId)) },
            navigateToBuddyGroupTag = { backStack.add(BuddyGroupTagListNavKey(navKey.buddyGroupId)) },
            navigateToBuddyGroupCalendar = { backStack.add(BuddyGroupCalendarNavKey(navKey.buddyGroupId)) },
            navigateToBuddyGroupFinishedMemo = { backStack.add(BuddyGroupFinishedMemoNavKey(navKey.buddyGroupId)) },
            navigateToBuddyGroupFinishedTag = { backStack.add(BuddyGroupFinishedTagNavKey(navKey.buddyGroupId)) },
            detailViewModel = koinViewModel { parametersOf(BuddyGroupId(navKey.buddyGroupId)) },
        )
    }

    buddyGroupMemoEntryProvider(backStack = backStack)
    buddyGroupTagEntryProvider(backStack = backStack)
    buddyGroupCalendarEntryProvider(backStack = backStack)
}

public fun EntryProviderScope<NavKey>.buddyGroupMemoEntryProvider(
    backStack: NavBackStack<NavKey>,
) {
    entry<BuddyGroupMemoListNavKey> { navKey ->
        BuddyGroupMemoListScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupMemoAdd = { backStack.add(BuddyGroupMemoAddNavKey(navKey.buddyGroupId)) },
            navigateToBuddyGroupMemoDetail = { memoId -> backStack.add(BuddyGroupMemoDetailNavKey(navKey.buddyGroupId, memoId)) },
            navigateToBuddyGroupMemoFilter = { backStack.add(BuddyGroupMemoListFilterNavKey(navKey.buddyGroupId)) },
            listViewModel = koinViewModel { parametersOf(BuddyGroupId(navKey.buddyGroupId)) },
            filterViewModel = koinViewModel { parametersOf(BuddyGroupId(navKey.buddyGroupId)) },
        )
    }

    entry<BuddyGroupMemoAddNavKey> { navKey ->
        val start = navKey.start
        val endInclusive = navKey.endInclusive

        BuddyGroupMemoAddScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupTagAdd = { backStack.add(BuddyGroupTagAddNavKey(navKey.buddyGroupId)) },
            navigateToBuddyGroupTag = { tagId -> backStack.add(BuddyGroupTagDetailNavKey(navKey.buddyGroupId, tagId)) },
            initialDateRange = if (start == null || endInclusive == null) {
                null
            } else {
                start..endInclusive
            },
            viewModel = koinViewModel { parametersOf(BuddyGroupId(navKey.buddyGroupId), PrimaryTag(navKey.primaryTag)) },
        )
    }

    entry<BuddyGroupMemoDetailNavKey> { navKey ->
        BuddyGroupMemoDetailScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupTagAdd = { backStack.add(BuddyGroupTagAddNavKey(navKey.buddyGroupId)) },
            navigateToBuddyGroupTag = { tagId -> backStack.add(BuddyGroupTagDetailNavKey(navKey.buddyGroupId, tagId)) },
            detailViewModel = koinViewModel { parametersOf(BuddyGroupId(navKey.buddyGroupId), MemoId(navKey.memoId)) },
            memoTagViewModel = koinViewModel { parametersOf(BuddyGroupId(navKey.buddyGroupId), MemoId(navKey.memoId)) },
        )
    }

    entry<BuddyGroupMemoListFilterNavKey>(
        metadata = DialogSceneStrategy.dialog(),
    ) { navKey ->
        BuddyGroupMemoListFilterDialog(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupTagAdd = { backStack.add(BuddyGroupTagAddNavKey(navKey.buddyGroupId)) },
            viewModel = koinViewModel { parametersOf(BuddyGroupId(navKey.buddyGroupId)) },
        )
    }

    entry<BuddyGroupFinishedMemoNavKey> { navKey ->
        BuddyGroupFinishMemoScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupMemoDetail = { memoId -> backStack.add(BuddyGroupMemoDetailNavKey(navKey.buddyGroupId, memoId)) },
            viewModel = koinViewModel { parametersOf(BuddyGroupId(navKey.buddyGroupId)) },
        )
    }
}

private fun EntryProviderScope<NavKey>.buddyGroupTagEntryProvider(
    backStack: NavBackStack<NavKey>,
) {
    entry<BuddyGroupTagListNavKey> { navKey ->
        BuddyGroupTagListScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupTagAdd = { backStack.add(BuddyGroupTagAddNavKey(navKey.buddyGroupId)) },
            navigateToBuddyGroupTagDetail = { tagId -> backStack.add(BuddyGroupTagDetailNavKey(navKey.buddyGroupId, tagId)) },
            navigateToBuddyGroupTagMemo = { tagId -> backStack.add(BuddyGroupTagMemoNavKey(navKey.buddyGroupId, tagId)) },
            viewModel = koinViewModel { parametersOf(BuddyGroupId(navKey.buddyGroupId)) },
        )
    }

    entry<BuddyGroupTagAddNavKey> { navKey ->
        BuddyGroupTagAddScreen(
            navigateUp = backStack::removeLastOrNull,
            viewModel = koinViewModel { parametersOf(BuddyGroupId(navKey.buddyGroupId)) },
        )
    }

    entry<BuddyGroupTagDetailNavKey> { navKey ->
        BuddyGroupTagDetailScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupTagMemo = { backStack.add(BuddyGroupTagMemoNavKey(navKey.buddyGroupId, navKey.tagId)) },
            viewModel = koinViewModel { parametersOf(BuddyGroupId(navKey.buddyGroupId), TagId(navKey.tagId)) },
        )
    }

    entry<BuddyGroupTagMemoNavKey> { navKey ->
        BuddyGroupTagMemoScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupMemoAdd = { backStack.add(BuddyGroupMemoAddNavKey(buddyGroupId = navKey.buddyGroupId, primaryTag = navKey.tagId)) },
            navigateToBuddyGroupMemoDetail = { memoId -> backStack.add(BuddyGroupMemoDetailNavKey(navKey.buddyGroupId, memoId)) },
            viewModel = koinViewModel { parametersOf(BuddyGroupId(navKey.buddyGroupId), TagId(navKey.tagId)) },
        )
    }

    entry<BuddyGroupFinishedTagNavKey> { navKey ->
        BuddyGroupFinishedTagScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupTagDetail = { tagId -> backStack.add(BuddyGroupTagDetailNavKey(navKey.buddyGroupId, tagId)) },
            navigateToBuddyGroupTagMemo = { tagId -> backStack.add(BuddyGroupTagMemoNavKey(navKey.buddyGroupId, tagId)) },
            viewModel = koinViewModel { parametersOf(BuddyGroupId(navKey.buddyGroupId)) },
        )
    }
}

private fun EntryProviderScope<NavKey>.buddyGroupCalendarEntryProvider(
    backStack: NavBackStack<NavKey>,
) {
    entry<BuddyGroupCalendarNavKey> { navKey ->
        BuddyGroupCalendarScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupCalendarFilter = { backStack.add(BuddyGroupCalendarFilterNavKey(navKey.buddyGroupId)) },
            navigateToBuddyGroupMemoAdd = { dateRange -> backStack.add(BuddyGroupMemoAddNavKey(buddyGroupId = navKey.buddyGroupId, start = dateRange.start, endInclusive = dateRange.endInclusive)) },
            navigateToBuddyGroupMemoDetail = { memoId -> backStack.add(BuddyGroupMemoDetailNavKey(navKey.buddyGroupId, memoId)) },
            calendarViewModel = koinViewModel { parametersOf(BuddyGroupId(navKey.buddyGroupId)) },
            filterViewModel = koinViewModel { parametersOf(BuddyGroupId(navKey.buddyGroupId)) },
        )
    }

    entry<BuddyGroupCalendarFilterNavKey>(
        metadata = DialogSceneStrategy.dialog(),
    ) { navKey ->
        BuddyGroupCalendarFilterDialog(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupTagAdd = { backStack.add(BuddyGroupTagAddNavKey(navKey.buddyGroupId)) },
            viewModel = koinViewModel { parametersOf(BuddyGroupId(navKey.buddyGroupId)) },
        )
    }
}

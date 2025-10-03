package io.github.taetae98coding.diary.feature.buddy.group

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import io.github.taetae98coding.diary.core.navigation.buddy.BuddyGroupAddNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.BuddyGroupCalendarNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.BuddyGroupDetailNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.BuddyGroupListNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.memo.BuddyGroupMemoAddNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.memo.BuddyGroupMemoDetailNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.memo.BuddyGroupMemoListNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.tag.BuddyGroupTagAddNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.tag.BuddyGroupTagDetailNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.tag.BuddyGroupTagListNavKey
import io.github.taetae98coding.diary.core.navigation.buddy.tag.BuddyGroupTagMemoNavKey
import io.github.taetae98coding.diary.core.navigation.login.LoginNavKey
import io.github.taetae98coding.diary.feature.buddy.group.add.BuddyGroupAddScreen
import io.github.taetae98coding.diary.feature.buddy.group.calendar.BuddyGroupCalendarScreen
import io.github.taetae98coding.diary.feature.buddy.group.detail.BuddyGroupDetailScreen
import io.github.taetae98coding.diary.feature.buddy.group.list.BuddyGroupListScreen
import io.github.taetae98coding.diary.feature.buddy.group.memo.add.BuddyGroupMemoAddScreen
import io.github.taetae98coding.diary.feature.buddy.group.memo.add.BuddyGroupMemoAddViewModel
import io.github.taetae98coding.diary.feature.buddy.group.memo.detail.BuddyGroupMemoDetailScreen
import io.github.taetae98coding.diary.feature.buddy.group.memo.detail.BuddyGroupMemoDetailViewModel
import io.github.taetae98coding.diary.feature.buddy.group.memo.detail.BuddyGroupMemoTagViewModel
import io.github.taetae98coding.diary.feature.buddy.group.memo.list.BuddyGroupMemoListScreen
import io.github.taetae98coding.diary.feature.buddy.group.tag.add.BuddyGroupTagAddScreen
import io.github.taetae98coding.diary.feature.buddy.group.tag.detail.BuddyGroupTagDetailScreen
import io.github.taetae98coding.diary.feature.buddy.group.tag.detail.BuddyGroupTagDetailViewModel
import io.github.taetae98coding.diary.feature.buddy.group.tag.list.BuddyGroupTagListScreen
import io.github.taetae98coding.diary.feature.buddy.group.tag.memo.BuddyGroupTagMemoScreen
import io.github.taetae98coding.diary.feature.buddy.group.tag.memo.BuddyGroupTagMemoViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

public fun EntryProviderBuilder<NavKey>.buddyGroupEntryProvider(
    backStack: NavBackStack<NavKey>,
    scrollState: BuddyGroupScrollState,
) {
    entry<BuddyGroupListNavKey> { navKey ->
        BuddyGroupListScreen(
            scrollState = scrollState,
            navigateToLogin = { backStack.add(LoginNavKey) },
            navigateToBuddyGroupAdd = { backStack.add(BuddyGroupAddNavKey) },
            navigateToBuddyGroupDetail = { backStack.add(BuddyGroupDetailNavKey(it)) },
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
            detailViewModel = koinViewModel { parametersOf(navKey.buddyGroupId) },
        )
    }

    buddyGroupMemoEntryProvider(backStack = backStack)
    buddyGroupTagEntryProvider(backStack = backStack)
    buddyGroupCalendarEntryProvider(backStack = backStack)
}

public fun EntryProviderBuilder<NavKey>.buddyGroupMemoEntryProvider(
    backStack: NavBackStack<NavKey>,
) {
    entry<BuddyGroupMemoListNavKey> { navKey ->
        BuddyGroupMemoListScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupMemoAdd = { backStack.add(BuddyGroupMemoAddNavKey(navKey.buddyGroupId)) },
            navigateToBuddyGroupMemoDetail = { memoId -> backStack.add(BuddyGroupMemoDetailNavKey(navKey.buddyGroupId, memoId)) },
            listViewModel = koinViewModel { parametersOf(navKey.buddyGroupId) },
        )
    }

    entry<BuddyGroupMemoAddNavKey> { navKey ->
        val start = navKey.start
        val endInclusive = navKey.endInclusive

        BuddyGroupMemoAddScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupTagAdd = { backStack.add(BuddyGroupTagAddNavKey(navKey.buddyGroupId)) },
            navigateToBuddyGroupTag = { backStack.add(BuddyGroupTagDetailNavKey(navKey.buddyGroupId, it)) },
            initialDateRange = if (start == null || endInclusive == null) {
                null
            } else {
                start..endInclusive
            },
            addViewModel = koinViewModel { parametersOf(BuddyGroupMemoAddViewModel.BuddyGroupId(navKey.buddyGroupId), BuddyGroupMemoAddViewModel.PrimaryTag(navKey.primaryTag)) },
        )
    }

    entry<BuddyGroupMemoDetailNavKey> { navKey ->
        BuddyGroupMemoDetailScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupTagAdd = { backStack.add(BuddyGroupTagAddNavKey(navKey.buddyGroupId)) },
            navigateToBuddyGroupTag = { backStack.add(BuddyGroupTagDetailNavKey(navKey.buddyGroupId, it)) },
            detailViewModel = koinViewModel { parametersOf(BuddyGroupMemoDetailViewModel.BuddyGroupId(navKey.buddyGroupId), BuddyGroupMemoDetailViewModel.MemoId(navKey.memoId)) },
            memoTagViewModel = koinViewModel { parametersOf(BuddyGroupMemoTagViewModel.BuddyGroupId(navKey.buddyGroupId), BuddyGroupMemoTagViewModel.MemoId(navKey.memoId)) },
        )
    }
}

private fun EntryProviderBuilder<NavKey>.buddyGroupTagEntryProvider(
    backStack: NavBackStack<NavKey>,
) {
    entry<BuddyGroupTagListNavKey> { navKey ->
        BuddyGroupTagListScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupTagAdd = { backStack.add(BuddyGroupTagAddNavKey(navKey.buddyGroupId)) },
            navigateToBuddyGroupTagDetail = { backStack.add(BuddyGroupTagDetailNavKey(navKey.buddyGroupId, it)) },
            listViewModel = koinViewModel { parametersOf(navKey.buddyGroupId) },
        )
    }

    entry<BuddyGroupTagAddNavKey> { navKey ->
        BuddyGroupTagAddScreen(
            navigateUp = backStack::removeLastOrNull,
            addViewModel = koinViewModel { parametersOf(navKey.buddyGroupId) },
        )
    }

    entry<BuddyGroupTagDetailNavKey> { navKey ->
        BuddyGroupTagDetailScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupTagMemo = { backStack.add(BuddyGroupTagMemoNavKey(navKey.buddyGroupId, navKey.tagId)) },
            detailViewModel = koinViewModel { parametersOf(BuddyGroupTagDetailViewModel.BuddyGroupId(navKey.buddyGroupId), BuddyGroupTagDetailViewModel.TagId(navKey.tagId)) },
        )
    }

    entry<BuddyGroupTagMemoNavKey> { navKey ->
        BuddyGroupTagMemoScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupMemoAdd = { backStack.add(BuddyGroupMemoAddNavKey(buddyGroupId = navKey.buddyGroupId, primaryTag = navKey.tagId)) },
            navigateToBuddyGroupMemoDetail = { backStack.add(BuddyGroupMemoDetailNavKey(navKey.buddyGroupId, it)) },
            tagMemoViewModel = koinViewModel { parametersOf(BuddyGroupTagMemoViewModel.BuddyGroupId(navKey.buddyGroupId), BuddyGroupTagMemoViewModel.TagId(navKey.tagId)) },
        )
    }
}

private fun EntryProviderBuilder<NavKey>.buddyGroupCalendarEntryProvider(
    backStack: NavBackStack<NavKey>,
) {
    entry<BuddyGroupCalendarNavKey> { navKey ->
        BuddyGroupCalendarScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToBuddyGroupMemoAdd = { backStack.add(BuddyGroupMemoAddNavKey(buddyGroupId = navKey.buddyGroupId, start = it.start, endInclusive = it.endInclusive)) },
            navigateToBuddyGroupMemoDetail = { backStack.add(BuddyGroupMemoDetailNavKey(navKey.buddyGroupId, it)) },
            calendarViewModel = koinViewModel { parametersOf(navKey.buddyGroupId) },
        )
    }
}

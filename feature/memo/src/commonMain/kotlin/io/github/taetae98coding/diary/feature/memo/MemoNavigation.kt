package io.github.taetae98coding.diary.feature.memo

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import io.github.taetae98coding.diary.core.navigation.memo.ListMemoFilterNavKey
import io.github.taetae98coding.diary.core.navigation.memo.MemoAddNavKey
import io.github.taetae98coding.diary.core.navigation.memo.MemoDetailNavKey
import io.github.taetae98coding.diary.core.navigation.memo.MemoListNavKey
import io.github.taetae98coding.diary.core.navigation.tag.TagAddNavKey
import io.github.taetae98coding.diary.core.navigation.tag.TagDetailNavKey
import io.github.taetae98coding.diary.feature.memo.add.MemoAddScreen
import io.github.taetae98coding.diary.feature.memo.detail.MemoDetailScreen
import io.github.taetae98coding.diary.feature.memo.filter.ListMemoFilterDialog
import io.github.taetae98coding.diary.feature.memo.list.MemoListScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

public fun EntryProviderBuilder<NavKey>.memoEntryProvider(
    backStack: NavBackStack<NavKey>,
    scrollState: MemoScrollState,
) {
    entry<MemoListNavKey> {
        MemoListScreen(
            scrollState = scrollState,
            navigateToListMemoFilter = { backStack.add(ListMemoFilterNavKey) },
            navigateToMemoAdd = { backStack.add(MemoAddNavKey()) },
            navigateToMemoDetail = { backStack.add(MemoDetailNavKey(it)) },
        )
    }

    entry<MemoAddNavKey> { navKey ->
        val start = navKey.start
        val endInclusive = navKey.endInclusive

        MemoAddScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToTagAdd = { backStack.add(TagAddNavKey) },
            navigateToTagDetail = { backStack.add(TagDetailNavKey(it)) },
            initialDateRange = if (start == null || endInclusive == null) {
                null
            } else {
                start..endInclusive
            },
            addViewModel = koinViewModel { parametersOf(navKey.primaryTag) },
        )
    }

    entry<MemoDetailNavKey> { navKey ->
        MemoDetailScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToTagAdd = { backStack.add(TagAddNavKey) },
            navigateToTagDetail = { backStack.add(TagDetailNavKey(it)) },
            detailViewModel = koinViewModel { parametersOf(navKey.memoId) },
            memoTagViewModel = koinViewModel { parametersOf(navKey.memoId) },
        )
    }

    entry<ListMemoFilterNavKey> {
        ListMemoFilterDialog(
            navigateUp = backStack::removeLastOrNull,
        )
    }
}

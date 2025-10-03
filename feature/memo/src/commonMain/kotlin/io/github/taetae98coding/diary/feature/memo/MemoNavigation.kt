package io.github.taetae98coding.diary.feature.memo

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.DialogSceneStrategy
import io.github.taetae98coding.diary.core.navigation.memo.MemoAddNavKey
import io.github.taetae98coding.diary.core.navigation.memo.MemoDetailNavKey
import io.github.taetae98coding.diary.core.navigation.memo.MemoListFilterNavKey
import io.github.taetae98coding.diary.core.navigation.memo.MemoListNavKey
import io.github.taetae98coding.diary.core.navigation.parameter.MemoId
import io.github.taetae98coding.diary.core.navigation.parameter.PrimaryTag
import io.github.taetae98coding.diary.core.navigation.tag.TagAddNavKey
import io.github.taetae98coding.diary.core.navigation.tag.TagDetailNavKey
import io.github.taetae98coding.diary.feature.memo.add.MemoAddScreen
import io.github.taetae98coding.diary.feature.memo.detail.MemoDetailScreen
import io.github.taetae98coding.diary.feature.memo.filter.MemoListFilterDialog
import io.github.taetae98coding.diary.feature.memo.list.MemoListScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

public fun EntryProviderScope<NavKey>.memoEntryProvider(
    backStack: NavBackStack<NavKey>,
    scrollState: MemoScrollState,
) {
    entry<MemoListNavKey> {
        MemoListScreen(
            scrollState = scrollState,
            navigateToMemoListFilter = { backStack.add(MemoListFilterNavKey) },
            navigateToMemoAdd = { backStack.add(MemoAddNavKey()) },
            navigateToMemoDetail = { memoId -> backStack.add(MemoDetailNavKey(memoId)) },
        )
    }

    entry<MemoAddNavKey> { navKey ->
        val start = navKey.start
        val endInclusive = navKey.endInclusive

        MemoAddScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToTagAdd = { backStack.add(TagAddNavKey) },
            navigateToTagDetail = { tagId -> backStack.add(TagDetailNavKey(tagId)) },
            initialDateRange = if (start == null || endInclusive == null) {
                null
            } else {
                start..endInclusive
            },
            viewModel = koinViewModel { parametersOf(PrimaryTag(navKey.primaryTag)) },
        )
    }

    entry<MemoDetailNavKey> { navKey ->
        MemoDetailScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToTagAdd = { backStack.add(TagAddNavKey) },
            navigateToTagDetail = { tagId -> backStack.add(TagDetailNavKey(tagId)) },
            detailViewModel = koinViewModel { parametersOf(MemoId(navKey.memoId)) },
            memoTagViewModel = koinViewModel { parametersOf(MemoId(navKey.memoId)) },
        )
    }

    entry<MemoListFilterNavKey>(
        metadata = DialogSceneStrategy.dialog(),
    ) {
        MemoListFilterDialog(
            navigateUp = backStack::removeLastOrNull,
            navigateToTagAdd = { backStack.add(TagAddNavKey) },
        )
    }
}

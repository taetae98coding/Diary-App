package io.github.taetae98coding.diary.feature.tag

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import io.github.taetae98coding.diary.core.navigation.memo.MemoAddNavKey
import io.github.taetae98coding.diary.core.navigation.memo.MemoDetailNavKey
import io.github.taetae98coding.diary.core.navigation.parameter.TagId
import io.github.taetae98coding.diary.core.navigation.tag.TagAddNavKey
import io.github.taetae98coding.diary.core.navigation.tag.TagDetailNavKey
import io.github.taetae98coding.diary.core.navigation.tag.TagListNavKey
import io.github.taetae98coding.diary.core.navigation.tag.TagMemoNavKey
import io.github.taetae98coding.diary.feature.tag.add.TagAddScreen
import io.github.taetae98coding.diary.feature.tag.detail.TagDetailScreen
import io.github.taetae98coding.diary.feature.tag.list.TagListScreen
import io.github.taetae98coding.diary.feature.tag.memo.TagMemoScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

public fun EntryProviderScope<NavKey>.tagEntryProvider(
    backStack: NavBackStack<NavKey>,
    scrollState: TagScrollState,
) {
    entry<TagListNavKey> {
        TagListScreen(
            scrollState = scrollState,
            navigateToTagAdd = { backStack.add(TagAddNavKey) },
            navigateToTagDetail = { tagId -> backStack.add(TagDetailNavKey(tagId)) },
            navigateToTagMemo = { tagId -> backStack.add(TagMemoNavKey(tagId)) },
        )
    }

    entry<TagAddNavKey> {
        TagAddScreen(
            navigateUp = backStack::removeLastOrNull,
        )
    }

    entry<TagDetailNavKey> { navKey ->
        TagDetailScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToTagMemo = { backStack.add(TagMemoNavKey(navKey.tagId)) },
            viewModel = koinViewModel { parametersOf(TagId(navKey.tagId)) },
        )
    }

    entry<TagMemoNavKey> { navKey ->
        TagMemoScreen(
            navigateUp = backStack::removeLastOrNull,
            navigateToMemoAdd = { backStack.add(MemoAddNavKey(primaryTag = navKey.tagId)) },
            navigateToMemoDetail = { memoId -> backStack.add(MemoDetailNavKey(memoId)) },
            viewModel = koinViewModel { parametersOf(TagId(navKey.tagId)) },
        )
    }
}

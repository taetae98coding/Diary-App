package io.github.taetae98coding.diary.feature.buddy.group.memo.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.library.lifecycle.common.ifResumed
import io.github.taetae98coding.diary.presenter.memo.adaptive.MemoDetailAdaptiveScaffold
import kotlin.uuid.Uuid
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BuddyGroupMemoDetailScreen(
    navigateUp: () -> Unit,
    navigateToBuddyGroupTagAdd: () -> Unit,
    navigateToBuddyGroupTag: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    detailViewModel: BuddyGroupMemoDetailViewModel = koinViewModel(),
    memoTagViewModel: BuddyGroupMemoTagViewModel = koinViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    MemoDetailAdaptiveScaffold(
        memoDetailStateHolder = detailViewModel,
        memoTagStateHolder = memoTagViewModel,
        onNavigateUp = dropUnlessResumed { navigateUp() },
        onTagAdd = dropUnlessResumed { navigateToBuddyGroupTagAdd() },
        onTag = { lifecycleOwner.ifResumed { navigateToBuddyGroupTag(it) } },
        modifier = modifier,
    )
}

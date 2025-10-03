package io.github.taetae98coding.diary.feature.buddy.group.tag.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.presenter.tag.detail.TagDetailScaffold
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BuddyGroupTagDetailScreen(
    navigateUp: () -> Unit,
    navigateToBuddyGroupTagMemo: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BuddyGroupTagDetailViewModel = koinViewModel(),
) {
    TagDetailScaffold(
        detailStateHolder = viewModel,
        onNavigateUp = dropUnlessResumed { navigateUp() },
        onMemo = dropUnlessResumed { navigateToBuddyGroupTagMemo() },
        modifier = modifier,
    )
}

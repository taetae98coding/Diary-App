package io.github.taetae98coding.diary.feature.buddy.group.memo.filter

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.taetae98coding.diary.presenter.memo.filter.MemoListFilterDialog

@Composable
internal fun BuddyGroupMemoListFilterDialog(
    navigateUp: () -> Unit,
    navigateToBuddyGroupTagAdd: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BuddyGroupMemoListFilterViewModel = viewModel(),
) {
    MemoListFilterDialog(
        stateHolder = viewModel,
        onDismissRequest = navigateUp,
        onAddTag = navigateToBuddyGroupTagAdd,
        modifier = modifier,
    )
}

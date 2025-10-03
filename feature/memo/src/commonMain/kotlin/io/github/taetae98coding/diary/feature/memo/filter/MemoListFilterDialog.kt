package io.github.taetae98coding.diary.feature.memo.filter

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.taetae98coding.diary.presenter.memo.filter.MemoListFilterDialog
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MemoListFilterDialog(
    navigateUp: () -> Unit,
    navigateToTagAdd: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MemoListFilterViewModel = koinViewModel(),
) {
    MemoListFilterDialog(
        stateHolder = viewModel,
        onDismissRequest = navigateUp,
        onAddTag = navigateToTagAdd,
        modifier = modifier,
    )
}

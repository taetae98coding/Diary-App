package io.github.taetae98coding.diary.feature.buddy.group.memo.finish

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.taetae98coding.diary.presenter.memo.finish.FinishedMemoScaffold
import kotlin.uuid.Uuid
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BuddyGroupFinishMemoScreen(
    navigateUp: () -> Unit,
    navigateToBuddyGroupMemoDetail: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BuddyGroupFinishMemoViewModel = koinViewModel(),
) {
    FinishedMemoScaffold(
        stateHolder = viewModel,
        onNavigateUp = navigateUp,
        onMemo = navigateToBuddyGroupMemoDetail,
        modifier = modifier,
    )
}

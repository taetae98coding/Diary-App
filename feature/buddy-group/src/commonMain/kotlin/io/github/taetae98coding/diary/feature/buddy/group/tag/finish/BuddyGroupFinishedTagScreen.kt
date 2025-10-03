package io.github.taetae98coding.diary.feature.buddy.group.tag.finish

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.taetae98coding.diary.presenter.tag.finish.FinishedTagScaffold
import kotlin.uuid.Uuid
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BuddyGroupFinishedTagScreen(
    navigateUp: () -> Unit,
    navigateToBuddyGroupTagDetail: (Uuid) -> Unit,
    navigateToBuddyGroupTagMemo: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BuddyGroupFinishedTagViewModel = koinViewModel(),
) {
    FinishedTagScaffold(
        stateHolder = viewModel,
        onNavigateUp = navigateUp,
        onTag = navigateToBuddyGroupTagDetail,
        onTagMemo = navigateToBuddyGroupTagMemo,
        modifier = modifier,
    )
}

package io.github.taetae98coding.diary.feature.buddy.group.tag.add

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.presenter.tag.add.TagAddScaffold
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BuddyGroupTagAddScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BuddyGroupTagAddViewModel = koinViewModel(),
) {
    TagAddScaffold(
        addStateHolder = viewModel,
        onNavigateUp = dropUnlessResumed { navigateUp() },
        title = { Text(text = "버디 그룹 태그 추가") },
        modifier = modifier,
    )
}

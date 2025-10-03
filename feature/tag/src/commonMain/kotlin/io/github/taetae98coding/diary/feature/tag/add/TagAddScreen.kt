package io.github.taetae98coding.diary.feature.tag.add

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.presenter.tag.add.TagAddScaffold
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun TagAddScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TagAddViewModel = koinViewModel(),
) {
    TagAddScaffold(
        addStateHolder = viewModel,
        onNavigateUp = dropUnlessResumed { navigateUp() },
        title = { Text(text = "태그 추가") },
        modifier = modifier,
    )
}

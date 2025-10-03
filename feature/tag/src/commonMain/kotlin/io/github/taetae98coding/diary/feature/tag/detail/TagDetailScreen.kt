package io.github.taetae98coding.diary.feature.tag.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.library.lifecycle.common.ifResumed
import io.github.taetae98coding.diary.presenter.tag.detail.TagDetailScaffold
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun TagDetailScreen(
    navigateUp: () -> Unit,
    navigateToTagMemo: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TagDetailViewModel = koinViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    TagDetailScaffold(
        detailStateHolder = viewModel,
        onNavigateUp = dropUnlessResumed { navigateUp() },
        onMemo = { lifecycleOwner.ifResumed { navigateToTagMemo() } },
        modifier = modifier,
    )
}

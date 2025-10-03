package io.github.taetae98coding.diary.feature.more.finish

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.library.lifecycle.common.ifResumed
import io.github.taetae98coding.diary.presenter.tag.finish.FinishedTagScaffold
import kotlin.uuid.Uuid
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun FinishedTagScreen(
    navigateUp: () -> Unit,
    navigateToTagDetail: (Uuid) -> Unit,
    navigateToTagMemo: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FinishTagViewModel = koinViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    FinishedTagScaffold(
        stateHolder = viewModel,
        onNavigateUp = dropUnlessResumed { navigateUp() },
        onTag = { lifecycleOwner.ifResumed { navigateToTagDetail(it) } },
        onTagMemo = { lifecycleOwner.ifResumed { navigateToTagMemo(it) } },
        modifier = modifier,
    )
}

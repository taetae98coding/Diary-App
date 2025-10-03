package io.github.taetae98coding.diary.feature.more.finish

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.library.lifecycle.common.ifResumed
import io.github.taetae98coding.diary.presenter.memo.finish.FinishedMemoScaffold
import kotlin.uuid.Uuid
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun FinishedMemoScreen(
    navigateUp: () -> Unit,
    navigateToMemoDetail: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FinishMemoViewModel = koinViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    FinishedMemoScaffold(
        stateHolder = viewModel,
        onNavigateUp = dropUnlessResumed { navigateUp() },
        onMemo = { lifecycleOwner.ifResumed { navigateToMemoDetail(it) } },
        modifier = modifier,
    )
}

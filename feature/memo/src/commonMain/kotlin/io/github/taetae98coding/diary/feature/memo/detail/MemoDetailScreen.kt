package io.github.taetae98coding.diary.feature.memo.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.library.lifecycle.common.ifResumed
import io.github.taetae98coding.diary.presenter.memo.adaptive.MemoDetailAdaptiveScaffold
import kotlin.uuid.Uuid
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MemoDetailScreen(
    navigateUp: () -> Unit,
    navigateToTagAdd: () -> Unit,
    navigateToTagDetail: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    detailViewModel: MemoDetailViewModel = koinViewModel(),
    memoTagViewModel: MemoTagViewModel = koinViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    MemoDetailAdaptiveScaffold(
        memoDetailStateHolder = detailViewModel,
        memoTagStateHolder = memoTagViewModel,
        onNavigateUp = dropUnlessResumed { navigateUp() },
        onTagAdd = dropUnlessResumed { navigateToTagAdd() },
        onTag = { lifecycleOwner.ifResumed { navigateToTagDetail(it) } },
        modifier = modifier,
    )
}

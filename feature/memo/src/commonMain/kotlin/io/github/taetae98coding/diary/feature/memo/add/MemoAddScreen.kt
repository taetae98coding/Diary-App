package io.github.taetae98coding.diary.feature.memo.add

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.library.lifecycle.common.ifResumed
import io.github.taetae98coding.diary.presenter.memo.adaptive.MemoAddAdaptiveScaffold
import kotlin.uuid.Uuid
import kotlinx.datetime.LocalDateRange
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MemoAddScreen(
    navigateUp: () -> Unit,
    navigateToTagAdd: () -> Unit,
    navigateToTagDetail: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    initialDateRange: LocalDateRange? = null,
    viewModel: MemoAddViewModel = koinViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    MemoAddAdaptiveScaffold(
        initialDateRange = initialDateRange,
        addStateHolder = viewModel,
        onNavigateUp = dropUnlessResumed { navigateUp() },
        onTagAdd = dropUnlessResumed { navigateToTagAdd() },
        onTag = { lifecycleOwner.ifResumed { navigateToTagDetail(it) } },
        memoAddTitle = { Text(text = "메모 추가") },
        modifier = modifier,
    )
}

package io.github.taetae98coding.diary.feature.buddy.group.memo.add

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
internal fun BuddyGroupMemoAddScreen(
    navigateUp: () -> Unit,
    navigateToBuddyGroupTagAdd: () -> Unit,
    navigateToBuddyGroupTag: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    initialDateRange: LocalDateRange? = null,
    viewModel: BuddyGroupMemoAddViewModel = koinViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    MemoAddAdaptiveScaffold(
        addStateHolder = viewModel,
        memoAddTitle = { Text(text = "버디 그룹 메모 추가") },
        onNavigateUp = dropUnlessResumed { navigateUp() },
        onTagAdd = dropUnlessResumed { navigateToBuddyGroupTagAdd() },
        onTag = { lifecycleOwner.ifResumed { navigateToBuddyGroupTag(it) } },
        modifier = modifier,
        initialDateRange = initialDateRange,
    )
}

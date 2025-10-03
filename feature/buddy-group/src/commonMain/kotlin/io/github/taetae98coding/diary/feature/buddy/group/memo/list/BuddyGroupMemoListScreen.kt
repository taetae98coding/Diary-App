package io.github.taetae98coding.diary.feature.buddy.group.memo.list

import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.compose.core.icon.NavigateUpIcon
import io.github.taetae98coding.diary.compose.core.icon.TagIcon
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.library.lifecycle.common.ifResumed
import io.github.taetae98coding.diary.presenter.memo.list.MemoListScaffold
import kotlin.uuid.Uuid
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BuddyGroupMemoListScreen(
    navigateUp: () -> Unit,
    navigateToBuddyGroupMemoAdd: () -> Unit,
    navigateToBuddyGroupMemoDetail: (Uuid) -> Unit,
    navigateToBuddyGroupMemoFilter: () -> Unit,
    modifier: Modifier = Modifier,
    listViewModel: BuddyGroupMemoListViewModel = koinViewModel(),
    filterViewModel: BuddyGroupMemoListFilterViewModel = koinViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    MemoListScaffold(
        listStateHolder = listViewModel,
        onAdd = dropUnlessResumed { navigateToBuddyGroupMemoAdd() },
        onMemo = { lifecycleOwner.ifResumed { navigateToBuddyGroupMemoDetail(it) } },
        modifier = modifier,
        title = { Text(text = "버디 그룹 메모") },
        navigationIcon = {
            IconButton(onClick = dropUnlessResumed { navigateUp() }) {
                NavigateUpIcon()
            }
        },
        actions = {
            val hasFilter by filterViewModel.hasFilter.collectAsStateWithLifecycle()

            IconButton(
                onClick = dropUnlessResumed { navigateToBuddyGroupMemoFilter() },
                colors = if (hasFilter) {
                    IconButtonDefaults.iconButtonColors(contentColor = DiaryTheme.colorScheme.primary)
                } else {
                    IconButtonDefaults.iconButtonColors()
                },
            ) {
                TagIcon()
            }
        },
    )
}

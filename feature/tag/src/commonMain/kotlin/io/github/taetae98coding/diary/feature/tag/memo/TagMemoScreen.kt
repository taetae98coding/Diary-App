package io.github.taetae98coding.diary.feature.tag.memo

import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.compose.core.icon.NavigateUpIcon
import io.github.taetae98coding.diary.library.lifecycle.common.ifResumed
import io.github.taetae98coding.diary.presenter.memo.list.MemoListScaffold
import kotlin.uuid.Uuid
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun TagMemoScreen(
    navigateUp: () -> Unit,
    navigateToMemoAdd: () -> Unit,
    navigateToMemoDetail: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TagMemoViewModel = koinViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val title by viewModel.title.collectAsStateWithLifecycle()

    MemoListScaffold(
        listStateHolder = viewModel,
        onAdd = dropUnlessResumed { navigateToMemoAdd() },
        onMemo = { lifecycleOwner.ifResumed { navigateToMemoDetail(it) } },
        modifier = modifier,
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = dropUnlessResumed { navigateUp() }) {
                NavigateUpIcon()
            }
        },
    )
}

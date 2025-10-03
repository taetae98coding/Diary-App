package io.github.taetae98coding.diary.feature.buddy.group.tag.list

import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.compose.core.icon.NavigateUpIcon
import io.github.taetae98coding.diary.library.lifecycle.common.ifResumed
import io.github.taetae98coding.diary.presenter.tag.list.TagListScaffold
import kotlin.uuid.Uuid
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BuddyGroupTagListScreen(
    navigateUp: () -> Unit,
    navigateToBuddyGroupTagAdd: () -> Unit,
    navigateToBuddyGroupTagDetail: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    listViewModel: BuddyGroupTagListViewModel = koinViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    TagListScaffold(
        listStateHolder = listViewModel,
        onAdd = dropUnlessResumed { navigateToBuddyGroupTagAdd() },
        onTag = { lifecycleOwner.ifResumed { navigateToBuddyGroupTagDetail(it) } },
        modifier = modifier,
        title = { Text("버디 그룹 태그") },
        navigationIcon = {
            IconButton(onClick = dropUnlessResumed { navigateUp() }) {
                NavigateUpIcon()
            }
        },
    )
}

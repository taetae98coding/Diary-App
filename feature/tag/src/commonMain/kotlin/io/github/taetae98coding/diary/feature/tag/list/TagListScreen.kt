package io.github.taetae98coding.diary.feature.tag.list

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.feature.tag.TagScrollState
import io.github.taetae98coding.diary.library.lifecycle.common.ifResumed
import io.github.taetae98coding.diary.presenter.tag.list.TagListScaffold
import io.github.taetae98coding.diary.presenter.tag.list.TagListScaffoldState
import io.github.taetae98coding.diary.presenter.tag.list.rememberTagListScaffoldState
import kotlin.uuid.Uuid
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun TagListScreen(
    scrollState: TagScrollState,
    navigateToTagAdd: () -> Unit,
    navigateToTagDetail: (Uuid) -> Unit,
    navigateToTagMemo: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TagListViewModel = koinViewModel(),
) {
    val state = rememberTagListScaffoldState()
    val lifecycleOwner = LocalLifecycleOwner.current

    TagListScaffold(
        listStateHolder = viewModel,
        onAdd = dropUnlessResumed { navigateToTagAdd() },
        onTag = { lifecycleOwner.ifResumed { navigateToTagDetail(it) } },
        onTagMemo = { lifecycleOwner.ifResumed { navigateToTagMemo(it) } },
        modifier = modifier,
        state = state,
        title = { Text("태그") },
    )

    ScrollEffect(
        state = state,
        scrollState = scrollState,
    )
}

@Composable
private fun ScrollEffect(
    state: TagListScaffoldState,
    scrollState: TagScrollState,
) {
    LaunchedEffect(
        state.lazyGridState,
        scrollState.hasToScrollTop,
    ) {
        if (scrollState.hasToScrollTop) {
            state.lazyGridState.animateScrollToItem(0)
            scrollState.onScrollTop()
        }
    }
}

package io.github.taetae98coding.diary.presenter.memo.adaptive

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import io.github.taetae98coding.diary.library.navigation3.compat.rememberCompatNavBackStack
import io.github.taetae98coding.diary.presenter.memo.detail.MemoDetailScaffold
import io.github.taetae98coding.diary.presenter.memo.detail.MemoDetailStateHolder
import io.github.taetae98coding.diary.presenter.memo.tag.MemoTagScaffold
import io.github.taetae98coding.diary.presenter.memo.tag.MemoTagStateHolder
import kotlin.uuid.Uuid
import kotlinx.serialization.Serializable

@Serializable
private data object MemoDetail : NavKey

@Serializable
private data object DetailMemoTag : NavKey

@Composable
public fun MemoDetailAdaptiveScaffold(
    memoDetailStateHolder: MemoDetailStateHolder,
    memoTagStateHolder: MemoTagStateHolder,
    onNavigateUp: () -> Unit,
    onTagAdd: () -> Unit,
    onTag: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    val backStack = rememberCompatNavBackStack(MemoDetail)

    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryProvider = entryProvider {
            entry<MemoDetail> {
                MemoDetailScaffold(
                    memoDetailStateHolder = memoDetailStateHolder,
                    memoTagStateHolder = memoTagStateHolder,
                    onNavigateUp = onNavigateUp,
                    onTagTitle = { backStack.add(DetailMemoTag) },
                    onTag = onTag,
                )
            }
            entry<DetailMemoTag> {
                MemoTagScaffold(
                    stateHolder = memoTagStateHolder,
                    onNavigateUp = backStack::removeLastOrNull,
                    onTagAdd = onTagAdd,
                )
            }
        },
    )
}

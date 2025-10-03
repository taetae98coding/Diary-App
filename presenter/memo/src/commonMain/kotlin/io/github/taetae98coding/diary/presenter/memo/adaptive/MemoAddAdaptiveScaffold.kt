package io.github.taetae98coding.diary.presenter.memo.adaptive

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import io.github.taetae98coding.diary.library.navigation3.compat.rememberCompatNavBackStack
import io.github.taetae98coding.diary.presenter.memo.add.MemoAddScaffold
import io.github.taetae98coding.diary.presenter.memo.add.MemoAddStateHolder
import io.github.taetae98coding.diary.presenter.memo.tag.MemoTagScaffold
import kotlin.uuid.Uuid
import kotlinx.datetime.LocalDateRange
import kotlinx.serialization.Serializable

@Serializable
private data object MemoAdd : NavKey

@Serializable
private data object AddMemoTag : NavKey

@Composable
public fun MemoAddAdaptiveScaffold(
    addStateHolder: MemoAddStateHolder,
    memoAddTitle: @Composable () -> Unit,
    onNavigateUp: () -> Unit,
    onTagAdd: () -> Unit,
    onTag: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    initialDateRange: LocalDateRange? = null,
) {
    val backStack = rememberCompatNavBackStack(MemoAdd)

    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryProvider = entryProvider {
            entry<MemoAdd> {
                MemoAddScaffold(
                    stateHolder = addStateHolder,
                    onNavigateUp = onNavigateUp,
                    onTag = onTag,
                    onTagTitle = { backStack.add(AddMemoTag) },
                    title = memoAddTitle,
                    initialDateRange = initialDateRange,
                )
            }
            entry<AddMemoTag> {
                MemoTagScaffold(
                    stateHolder = addStateHolder,
                    onNavigateUp = backStack::removeLastOrNull,
                    onTagAdd = onTagAdd,
                )
            }
        },
    )
}

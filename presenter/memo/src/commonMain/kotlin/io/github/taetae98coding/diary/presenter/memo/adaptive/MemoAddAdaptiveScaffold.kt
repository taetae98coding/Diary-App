package io.github.taetae98coding.diary.presenter.memo.adaptive

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.scene.rememberSceneSetupNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
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
    // TODO use material3-adaptive-navigation
    val backStack = remember { NavBackStack<NavKey>(MemoAdd) }

    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<MemoAdd> {
                MemoAddScaffold(
                    addStateHolder = addStateHolder,
                    onNavigateUp = onNavigateUp,
                    onTag = onTag,
                    onTagTitle = { backStack.add(AddMemoTag) },
                    title = memoAddTitle,
                    initialDateRange = initialDateRange,
                )
            }
            entry<AddMemoTag> {
                MemoTagScaffold(
                    memoTagStateHolder = addStateHolder,
                    onNavigateUp = backStack::removeLastOrNull,
                    onTagAdd = onTagAdd,
                )
            }
        },
        transitionSpec = {
            ContentTransform(
                fadeIn(animationSpec = tween(700)),
                fadeOut(animationSpec = tween(700)),
            )
        },
        popTransitionSpec = {
            ContentTransform(
                fadeIn(animationSpec = tween(700)),
                fadeOut(animationSpec = tween(700)),
            )
        },
        predictivePopTransitionSpec = {
            ContentTransform(
                fadeIn(spring(dampingRatio = 1.0f, stiffness = 1600.0f)),
                scaleOut(targetScale = 0.7f),
            )
        },
    )
}

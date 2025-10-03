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
    // TODO use material3-adaptive-navigation
    val backStack = remember { NavBackStack<NavKey>(MemoDetail) }

    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
        ),
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
                    memoTagStateHolder = memoTagStateHolder,
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

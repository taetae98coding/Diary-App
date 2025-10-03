package io.github.taetae98coding.diary.library.material3.adaptive.navigation

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldValue
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.PredictiveBackHandler
import kotlin.OptIn
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext

private const val SinglePaneProgressRatio: Float = 0.1f
private const val DualPaneProgressRatio: Float = 0.15f
private const val TriplePaneProgressRatio: Float = 0.2f

@Composable
public actual fun <T> PlatformListDetailPaneScaffold(
    navigator: ThreePaneScaffoldNavigator<T>,
    listPane: @Composable (() -> Unit),
    detailPane: @Composable (() -> Unit),
    modifier: Modifier,
) {
    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                listPane()
            }
        },
        detailPane = {
            AnimatedPane {
                detailPane()
            }
        },
        modifier = modifier,
    )

    PredictiveBackHandler(navigator.canNavigateBack()) { flow ->
        try {
            flow.collect { event ->
                navigator.seekBack(
                    fraction = backProgressToStateProgress(event.progress, navigator.scaffoldValue),
                )
            }

            navigator.navigateBack()
        } catch (_: CancellationException) {
            withContext(NonCancellable) {
                navigator.seekBack(fraction = 0F)
            }
        }
    }
}

private fun backProgressToStateProgress(progress: Float, scaffoldValue: ThreePaneScaffoldValue): Float {
    val predictiveBackEasing = CubicBezierEasing(0.1f, 0.1f, 0f, 1f)
    val expandedCount = listOf(scaffoldValue.primary, scaffoldValue.secondary, scaffoldValue.tertiary)
        .count { it == PaneAdaptedValue.Expanded }
    val ratio = when (expandedCount) {
        1 -> SinglePaneProgressRatio
        2 -> DualPaneProgressRatio
        else -> TriplePaneProgressRatio
    }

    return predictiveBackEasing.transform(progress) * ratio
}

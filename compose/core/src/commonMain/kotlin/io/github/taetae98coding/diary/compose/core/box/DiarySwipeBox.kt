package io.github.taetae98coding.diary.compose.core.box

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.taetae98coding.diary.compose.core.icon.CircleIcon

@Composable
public fun DiarySwipeBox(
    state: SwipeToDismissBoxState,
    onStartToEnd: () -> Unit,
    onEndToStart: () -> Unit,
    modifier: Modifier = Modifier,
    startIcon: @Composable (iconSize: Dp) -> Unit = {},
    endIcon: @Composable (iconSize: Dp) -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    SwipeToDismissBox(
        state = state,
        backgroundContent = {
            if (state.currentValue != SwipeToDismissBoxValue.EndToStart) {
                AnimateIconBox(
                    state = state,
                    targetValue = SwipeToDismissBoxValue.StartToEnd,
                    modifier = Modifier.width(56.dp)
                        .fillMaxHeight(),
                ) { iconSize ->
                    startIcon(iconSize)
                }
            }
            Spacer(modifier = Modifier.weight(1F))
            if (state.currentValue != SwipeToDismissBoxValue.StartToEnd) {
                AnimateIconBox(
                    state = state,
                    targetValue = SwipeToDismissBoxValue.EndToStart,
                    modifier = Modifier.width(56.dp)
                        .fillMaxHeight(),
                ) { iconSize ->
                    endIcon(iconSize)
                }
            }
        },
        modifier = modifier,
        onDismiss = { value ->
            when (value) {
                SwipeToDismissBoxValue.Settled -> Unit
                SwipeToDismissBoxValue.StartToEnd -> onStartToEnd()
                SwipeToDismissBoxValue.EndToStart -> onEndToStart()
            }
        },
        content = content,
    )
}

@Composable
private fun AnimateIconBox(
    state: SwipeToDismissBoxState,
    targetValue: SwipeToDismissBoxValue,
    modifier: Modifier = Modifier,
    icon: @Composable (Dp) -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        val isTargetState = state.currentValue == targetValue
        val animateSize by animateDpAsState(
            targetValue = if (isTargetState) {
                32.dp
            } else {
                16.dp
            },
        )
        Crossfade(
            targetState = isTargetState,
            modifier = Modifier.size(animateSize),
        ) { isTargetState ->
            if (isTargetState) {
                icon(animateSize)
            } else {
                CircleIcon(modifier = Modifier.size(animateSize))
            }
        }
    }
}

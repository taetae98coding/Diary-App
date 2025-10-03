package io.github.taetae98coding.diary.compose.core.box

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.taetae98coding.diary.compose.core.icon.DeleteIcon
import io.github.taetae98coding.diary.compose.core.icon.FinishIcon

@Composable
public fun FinishAndDeleteSwipeBox(
    state: SwipeToDismissBoxState,
    onFinish: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    DiarySwipeBox(
        state = state,
        onStartToEnd = onFinish,
        onEndToStart = onDelete,
        startIcon = { FinishIcon(modifier = Modifier.size(it)) },
        endIcon = { DeleteIcon(modifier = Modifier.size(it)) },
        modifier = modifier,
        content = content,
    )
}

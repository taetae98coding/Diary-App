package io.github.taetae98coding.diary.compose.core.box

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.taetae98coding.diary.compose.core.icon.DeleteIcon

@Composable
public fun DeleteSwipeBox(
    state: SwipeToDismissBoxState,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    DiarySwipeBox(
        state = state,
        onStartToEnd = onDelete,
        onEndToStart = onDelete,
        startIcon = { DeleteIcon(modifier = Modifier.size(it)) },
        endIcon = { DeleteIcon(modifier = Modifier.size(it)) },
        modifier = modifier,
        content = content,
    )
}

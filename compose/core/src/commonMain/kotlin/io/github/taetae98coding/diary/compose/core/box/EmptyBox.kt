package io.github.taetae98coding.diary.compose.core.box

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

public data object EmptyBox {
    public const val KEY: String = "EmptyBox"

    public data object ContentType
}

@Composable
public fun EmptyBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

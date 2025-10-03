package io.github.taetae98coding.diary.library.compose.foundation

import androidx.compose.foundation.focusable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester

public fun Modifier.shortcutFocus(
    focusRequester: FocusRequester,
): Modifier {
    return focusRequester(focusRequester)
        .focusable()
}

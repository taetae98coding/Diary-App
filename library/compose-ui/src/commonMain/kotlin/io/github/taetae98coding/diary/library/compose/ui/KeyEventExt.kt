package io.github.taetae98coding.diary.library.compose.ui

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.isMetaPressed

public fun KeyEvent.isPlatformMetaPressed(): Boolean {
    return isCtrlPressed || isMetaPressed
}

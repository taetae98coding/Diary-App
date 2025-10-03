package io.github.taetae98coding.diary.library.compose.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type

public fun Modifier.onPreviewKeyUp(
    onPreviewKeyEvent: (KeyEvent) -> Boolean,
): Modifier {
    return onPreviewKeyEvent { keyEvent ->
        if (keyEvent.type != KeyEventType.KeyUp) {
            return@onPreviewKeyEvent false
        }

        onPreviewKeyEvent(keyEvent)
    }
}

public fun Modifier.onAddShortcut(
    action: () -> Unit,
): Modifier {
    return onPreviewKeyUp { keyEvent ->
        when {
            keyEvent.isPlatformMetaPressed() && keyEvent.key == Key.A -> {
                action()
                true
            }

            else -> false
        }
    }
}

public fun Modifier.onDirectionLeftShortcut(
    action: () -> Unit,
): Modifier {
    return onPreviewKeyUp { keyEvent ->
        when {
            keyEvent.key == Key.DirectionLeft -> {
                action()
                true
            }

            else -> false
        }
    }
}

public fun Modifier.onDirectionRightShortcut(
    action: () -> Unit,
): Modifier {
    return onPreviewKeyUp { keyEvent ->
        when {
            keyEvent.key == Key.DirectionRight -> {
                action()
                true
            }

            else -> false
        }
    }
}

public fun Modifier.onActionShortcut(
    action: () -> Unit,
): Modifier {
    return onPreviewKeyUp { keyEvent ->
        when {
            keyEvent.isPlatformMetaPressed() && keyEvent.key == Key.Enter -> {
                action()
                true
            }

            else -> false
        }
    }
}

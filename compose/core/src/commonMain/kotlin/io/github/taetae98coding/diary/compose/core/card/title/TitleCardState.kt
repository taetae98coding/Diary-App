package io.github.taetae98coding.diary.compose.core.card.title

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.focus.FocusRequester

public data class TitleCardState(
    val textFieldState: TextFieldState,
    val focusRequester: FocusRequester,
)

@Composable
public fun rememberTitleCardState(
    vararg inputs: Any?,
    initialText: String = "",
): TitleCardState {
    val textFieldState = rememberSaveable(
        inputs = inputs,
        saver = TextFieldState.Saver,
    ) {
        TextFieldState(initialText = initialText)
    }
    val focusRequester = remember { FocusRequester() }

    return remember(
        textFieldState,
        focusRequester,
    ) {
        TitleCardState(
            textFieldState = textFieldState,
            focusRequester = focusRequester,
        )
    }
}

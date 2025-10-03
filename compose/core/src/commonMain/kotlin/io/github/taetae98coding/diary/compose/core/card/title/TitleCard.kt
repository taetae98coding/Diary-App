package io.github.taetae98coding.diary.compose.core.card.title

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import io.github.taetae98coding.diary.compose.core.text.ClearTextField

@Composable
public fun TitleCard(
    state: TitleCardState,
    modifier: Modifier = Modifier,
    nextFocusProperty: FocusRequester = FocusRequester.Default,
) {
    Card(modifier = modifier) {
        ClearTextField(
            state = state.textFieldState,
            modifier = Modifier.fillMaxWidth()
                .focusRequester(state.focusRequester)
                .focusProperties { next = nextFocusProperty },
            label = { Text(text = "제목") },
            keyboardOptions = KeyboardOptions.Default,
            onKeyboardAction = null,
            lineLimits = TextFieldLineLimits.SingleLine,
        )
    }
}

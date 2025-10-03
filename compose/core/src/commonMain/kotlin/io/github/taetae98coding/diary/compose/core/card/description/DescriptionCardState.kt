package io.github.taetae98coding.diary.compose.core.card.description

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.focus.FocusRequester

public data class DescriptionCardState(
    val textFieldState: TextFieldState,
    internal val pagerState: PagerState,
    internal val focusRequester: FocusRequester,
) {
    val page: DescriptionCardPage
        get() = if (pagerState.currentPage == INPUT) {
            DescriptionCardPage.Input
        } else {
            DescriptionCardPage.Markdown
        }

    val textFieldFocusRequester: FocusRequester?
        get() = if (pagerState.currentPage == INPUT) {
            focusRequester
        } else {
            null
        }

    public companion object {
        internal const val INPUT = 0
        internal const val MARKDOWN = 1
    }
}

@Composable
public fun rememberDescriptionCardState(
    vararg inputs: Any?,
    initialText: String = "",
): DescriptionCardState {
    val textFieldState = rememberSaveable(
        inputs = inputs,
        saver = TextFieldState.Saver,
    ) {
        TextFieldState(initialText)
    }
    val focusRequester = remember { FocusRequester() }

    return remember(
        textFieldState,
        focusRequester,
    ) {
        val pagerState = PagerState(
            currentPage = if (initialText.isBlank()) {
                DescriptionCardState.INPUT
            } else {
                DescriptionCardState.MARKDOWN
            },
        ) {
            DescriptionCardPage.entries.size
        }

        DescriptionCardState(
            textFieldState = textFieldState,
            pagerState = pagerState,
            focusRequester = focusRequester,
        )
    }
}

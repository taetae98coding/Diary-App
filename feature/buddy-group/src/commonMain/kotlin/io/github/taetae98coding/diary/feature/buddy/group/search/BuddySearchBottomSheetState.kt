package io.github.taetae98coding.diary.feature.buddy.group.search

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester

internal data class BuddySearchBottomSheetState(
    val sheetState: SheetState,
    val searchBarState: SearchBarState,
    val textFieldState: TextFieldState,
    val textFieldFocusRequester: FocusRequester,
)

@Composable
internal fun rememberBuddySearchBottomSheetState(): BuddySearchBottomSheetState {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val searchBarState = rememberSearchBarState(initialValue = SearchBarValue.Expanded)
    val textFieldState = rememberTextFieldState()
    val focusRequester = remember { FocusRequester() }

    return remember(
        sheetState,
        searchBarState,
        textFieldState,
        focusRequester,
    ) {
        BuddySearchBottomSheetState(
            sheetState = sheetState,
            searchBarState = searchBarState,
            textFieldState = textFieldState,
            textFieldFocusRequester = focusRequester,
        )
    }
}

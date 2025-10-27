package io.github.taetae98coding.diary.compose.core.date

import androidx.compose.material3.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
public fun DiaryDatePicker(
    state: DiaryDatePickerState,
    modifier: Modifier = Modifier,
) {
    DatePicker(
        state = state.datePickerState,
        modifier = modifier,
    )
}

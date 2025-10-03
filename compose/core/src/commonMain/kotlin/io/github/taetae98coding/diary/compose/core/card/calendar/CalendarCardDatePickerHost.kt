package io.github.taetae98coding.diary.compose.core.card.calendar

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.taetae98coding.diary.compose.core.date.DiaryDatePicker
import io.github.taetae98coding.diary.compose.core.date.rememberDiaryDatePickerState

@Composable
public fun CalendarCardDatePickerHost(
    state: CalendarCardState,
    modifier: Modifier = Modifier,
) {
    if (state.isDatePickerVisible != CalendarCardDatePickerVisible.NONE) {
        val datePickerState = when (state.isDatePickerVisible) {
            CalendarCardDatePickerVisible.START -> rememberDiaryDatePickerState(state.start)
            CalendarCardDatePickerVisible.END_INCLUSIVE -> rememberDiaryDatePickerState(state.endInclusive)
            CalendarCardDatePickerVisible.NONE -> return
        }

        DatePickerDialog(
            onDismissRequest = state::hideDatePicker,
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedLocalDate?.let {
                            when (state.isDatePickerVisible) {
                                CalendarCardDatePickerVisible.START -> state.updateStartDate(it)
                                CalendarCardDatePickerVisible.END_INCLUSIVE -> state.updateEndInclusiveDate(it)
                                CalendarCardDatePickerVisible.NONE -> Unit
                            }
                        }
                        state.hideDatePicker()
                    },
                    enabled = datePickerState.selectedLocalDate != null,
                ) {
                    Text(text = "확인")
                }
            },
        ) {
            DiaryDatePicker(
                state = datePickerState,
            )
        }
    }
}

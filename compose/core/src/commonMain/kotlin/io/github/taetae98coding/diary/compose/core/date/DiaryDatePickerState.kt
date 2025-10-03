package io.github.taetae98coding.diary.compose.core.date

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.taetae98coding.diary.library.kotlinx.datetime.localDate
import io.github.taetae98coding.diary.library.kotlinx.datetime.toEpochMilliseconds
import kotlin.time.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

public class DiaryDatePickerState internal constructor(
    internal val datePickerState: DatePickerState,
) {
    public val selectedLocalDate: LocalDate?
        get() = datePickerState.selectedDateMillis?.let { localDate(it) }
}

@Composable
public fun rememberDiaryDatePickerState(
    initialSelectedLocalDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
): DiaryDatePickerState {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialSelectedLocalDate.toEpochMilliseconds(),
    )

    return remember(
        datePickerState,
    ) {
        DiaryDatePickerState(
            datePickerState = datePickerState,
        )
    }
}

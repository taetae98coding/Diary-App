package io.github.taetae98coding.diary.compose.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.datetime.LocalDateRange

public class CalendarSelectState {
    internal var range by mutableStateOf<LocalDateRange?>(null)
        private set

    internal fun select(dateRange: LocalDateRange) {
        range = dateRange
    }

    internal fun unselect() {
        range = null
    }
}

@Composable
public fun rememberCalendarSelectState(): CalendarSelectState {
    return CalendarSelectState()
}

package io.github.taetae98coding.diary.presenter.calendar.scaffold

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import io.github.taetae98coding.diary.compose.calendar.CalendarState
import io.github.taetae98coding.diary.compose.calendar.rememberCalendarState
import kotlin.time.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlinx.datetime.yearMonth

public data class CalendarScaffoldState(
    val hostState: SnackbarHostState,
    val focusRequester: FocusRequester,
    val calendarState: CalendarState,
) {
    internal var today by mutableStateOf(now())
        private set

    internal var isDatePickerVisible by mutableStateOf(false)
        private set

    private fun now(): LocalDate {
        return Clock.System.todayIn(TimeZone.currentSystemDefault())
    }

    internal fun refreshToday() {
        today = now()
    }

    internal fun showDatePicker() {
        isDatePickerVisible = true
    }

    internal fun hideDatePicker() {
        isDatePickerVisible = false
    }

    public suspend fun animateScrollToToday() {
        calendarState.animateScrollTo(today.yearMonth)
    }

    public companion object {
        internal fun saver(
            hostState: SnackbarHostState,
            focusRequester: FocusRequester,
            calendarState: CalendarState,
        ): Saver<CalendarScaffoldState, Any> {
            return listSaver(
                save = {
                    listOf(
                        it.today.toEpochDays(),
                        it.isDatePickerVisible,
                    )
                },
                restore = {
                    CalendarScaffoldState(
                        hostState = hostState,
                        focusRequester = focusRequester,
                        calendarState = calendarState,
                    ).apply {
                        today = LocalDate.fromEpochDays(it[0] as Long)
                        isDatePickerVisible = it[1] as Boolean
                    }
                },
            )
        }
    }
}

@Composable
public fun rememberCalendarScaffoldState(): CalendarScaffoldState {
    val hostState = remember { SnackbarHostState() }
    val focusRequester = remember { FocusRequester() }
    val calendarState = rememberCalendarState()

    return rememberSaveable(
        inputs = arrayOf(
            hostState,
            focusRequester,
            calendarState,
        ),
        saver = CalendarScaffoldState.saver(
            hostState = hostState,
            focusRequester = focusRequester,
            calendarState = calendarState,
        ),
    ) {
        CalendarScaffoldState(
            hostState = hostState,
            focusRequester = focusRequester,
            calendarState = calendarState,
        )
    }
}

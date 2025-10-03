package io.github.taetae98coding.diary.compose.core.card.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlin.time.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateRange
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

public class CalendarCardState internal constructor(
    clock: Clock = Clock.System,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
    initialDateRange: LocalDateRange?,
) {
    internal var isDatePickerVisible by mutableStateOf(CalendarCardDatePickerVisible.NONE)
        private set

    public var hasDate: Boolean by mutableStateOf(initialDateRange != null)
        private set

    public var start: LocalDate by mutableStateOf(initialDateRange?.start ?: clock.todayIn(timeZone))
        private set
    public var endInclusive: LocalDate by mutableStateOf(initialDateRange?.endInclusive ?: clock.todayIn(timeZone))
        private set

    public val dateRange: LocalDateRange
        get() = start..endInclusive

    internal fun setDate() {
        hasDate = true
    }

    internal fun clearDate() {
        hasDate = false
    }

    internal fun updateStartDate(date: LocalDate) {
        start = date
        if (endInclusive < date) {
            endInclusive = date
        }
    }

    internal fun updateEndInclusiveDate(date: LocalDate) {
        endInclusive = date
        if (start > date) {
            start = date
        }
    }

    internal fun showStartDatePicker() {
        isDatePickerVisible = CalendarCardDatePickerVisible.START
    }

    internal fun showEndInclusiveDatePicker() {
        isDatePickerVisible = CalendarCardDatePickerVisible.END_INCLUSIVE
    }

    internal fun hideDatePicker() {
        isDatePickerVisible = CalendarCardDatePickerVisible.NONE
    }

    public companion object {
        internal fun saver(): Saver<CalendarCardState, Any> {
            return listSaver(
                save = {
                    listOf(
                        it.isDatePickerVisible.ordinal,
                        it.hasDate,
                        it.start.toEpochDays(),
                        it.endInclusive.toEpochDays(),
                    )
                },
                restore = {
                    val dateRange = LocalDate.fromEpochDays(it[2] as Long)..LocalDate.fromEpochDays(it[3] as Long)

                    CalendarCardState(
                        initialDateRange = dateRange,
                    ).apply {
                        isDatePickerVisible = CalendarCardDatePickerVisible.entries[it[0] as Int]
                        hasDate = it[1] as Boolean
                    }
                },
            )
        }
    }
}

@Composable
public fun rememberCalendarCardState(
    vararg inputs: Any?,
    initialDateRange: LocalDateRange? = null,
): CalendarCardState {
    return rememberSaveable(
        inputs = inputs,
        saver = CalendarCardState.saver(),
    ) {
        CalendarCardState(
            initialDateRange = initialDateRange,
        )
    }
}

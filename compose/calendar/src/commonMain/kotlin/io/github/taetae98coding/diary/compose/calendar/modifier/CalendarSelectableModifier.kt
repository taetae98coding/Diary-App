package io.github.taetae98coding.diary.compose.calendar.modifier

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import io.github.taetae98coding.diary.compose.calendar.CalendarState
import io.github.taetae98coding.diary.library.kotlinx.datetime.dayNumber
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateRange
import kotlinx.datetime.minus
import kotlinx.datetime.plus

@Composable
internal fun Modifier.calendarSelectable(
    state: CalendarState,
    onDragFinish: (LocalDateRange) -> Unit,
): Modifier {
    val feedback = LocalHapticFeedback.current
    val coroutineScope = rememberCoroutineScope()

    return pointerInput(state) {
        lateinit var baseLocalDate: LocalDate
        var job: Job? = null

        fun select(dateRange: LocalDateRange, type: HapticFeedbackType) {
            state.selectState.select(dateRange)
            feedback.performHapticFeedback(type)
        }

        detectDragGesturesAfterLongPress(
            onDragStart = { offset ->
                baseLocalDate = state.localDate(coordinate(offset))
                select(baseLocalDate..baseLocalDate, HapticFeedbackType.LongPress)
            },
            onDragEnd = {
                val temp = state.selectState.range
                state.selectState.unselect()
                feedback.performHapticFeedback(HapticFeedbackType.GestureEnd)

                temp?.let(onDragFinish)
            },
            onDragCancel = {
                state.selectState.unselect()
            },
            onDrag = { change, _ ->
                val changeLocalDate = state.localDate(coordinate(change.position))
                val dateRange = minOf(baseLocalDate, changeLocalDate)..maxOf(baseLocalDate, changeLocalDate)
                val isLeftEdge = change.position.x <= size.width / 7F / 3F
                val isRightEdge = change.position.x >= (size.width / 7F * 6F) + size.width / 7F * (2F / 3F)

                if (state.selectState.range != dateRange) {
                    select(dateRange, HapticFeedbackType.GestureThresholdActivate)
                }

                if (!state.pagerState.isScrollInProgress) {
                    if (isLeftEdge && job?.isActive != true) {
                        job = coroutineScope.launch {
                            state.animateScrollToPreviousPage()
                        }
                    } else if (isRightEdge && job?.isActive != true) {
                        job = coroutineScope.launch {
                            state.animateScrollToNextPage()
                        }
                    }
                }
            },
        )
    }
}

private fun PointerInputScope.coordinate(offset: Offset): Pair<Int, Int> {
    return (offset.x * 7F / size.width).toInt() to (offset.y * 6F / size.height).toInt()
}

private fun CalendarState.localDate(coordinate: Pair<Int, Int>): LocalDate {
    return yearMonth.firstDay.plus(coordinate.second, DateTimeUnit.WEEK)
        .let { it.minus(dayNumber(it.dayOfWeek, startDayOfWeek), DateTimeUnit.DAY) }
        .plus(coordinate.first, DateTimeUnit.DAY)
}

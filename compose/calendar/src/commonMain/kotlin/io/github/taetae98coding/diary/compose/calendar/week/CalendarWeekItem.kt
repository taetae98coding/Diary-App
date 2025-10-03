package io.github.taetae98coding.diary.compose.calendar.week

import androidx.compose.ui.graphics.Color
import io.github.taetae98coding.diary.compose.calendar.item.CalendarItemKey
import io.github.taetae98coding.diary.compose.calendar.item.CalendarItemUiState
import io.github.taetae98coding.diary.compose.calendar.item.CalendarTextItemUiState
import io.github.taetae98coding.diary.compose.calendar.item.CalendarWeatherIconUiState
import io.github.taetae98coding.diary.compose.calendar.item.CalendarWeatherUiState
import io.github.taetae98coding.diary.library.kotlinx.datetime.dayNumber
import io.github.taetae98coding.diary.library.kotlinx.datetime.dayOfWeek
import io.github.taetae98coding.diary.library.kotlinx.datetime.isOverlaps
import io.github.taetae98coding.diary.library.kotlinx.datetime.toEpochMilliseconds
import kotlin.math.abs
import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.Uuid
import kotlinx.datetime.LocalDateRange
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal sealed class CalendarWeekItem {
    abstract val span: Int
    abstract val key: Any
    abstract val contentType: Any

    data class Weather(
        val icon: List<CalendarWeatherIconUiState>,
        val temperature: Double,
        val instant: Instant,
        override val span: Int,
    ) : CalendarWeekItem() {
        override val key = instant.toEpochMilliseconds()
        override val contentType = "CalendarWeatherItemContentType"
    }

    data class ColorText(
        val calendarItemKey: CalendarItemKey,
        val text: String,
        val color: Color,
        override val span: Int,
    ) : CalendarWeekItem() {
        override val key = calendarItemKey.composeKey
        override val contentType = "CalendarHolidayItemContentType"
    }

    data class Holiday(
        override val key: Any,
        val text: String,
        val uri: String,
        override val span: Int,
    ) : CalendarWeekItem() {
        override val contentType = "CalendarHolidayItemContentType"
    }

    data class SpecialDay(
        override val key: Any,
        val text: String,
        val uri: String,
        override val span: Int = 1,
    ) : CalendarWeekItem() {
        override val contentType = "CalendarSpecialDayItemContentType"
    }

    data class Space(
        override val span: Int,
    ) : CalendarWeekItem() {
        override val key = Uuid.random().toString()
        override val contentType = "CalendarWeekItemSpaceContentType"
    }
}

internal fun combineCalendarWeekItem(
    weekRange: LocalDateRange,
    colorTextList: List<CalendarTextItemUiState.ColorText>,
    weatherList: List<CalendarWeatherUiState>,
    holidayList: List<CalendarTextItemUiState.Holiday>,
    specialDayList: List<CalendarTextItemUiState.SpecialDay>,
): List<CalendarWeekItem> {
    return buildList {
        addAll(
            weatherWeekItem(weekRange, weatherList),
        )
        addAll(
            textWeekItem(
                weekRange = weekRange,
                text = (holidayList + colorTextList.sortedBy { it.text } + specialDayList).sorted(),
            ),
        )
    }
}

private fun weatherWeekItem(
    weekRange: LocalDateRange,
    weather: List<CalendarWeatherUiState>,
): List<CalendarWeekItem> {
    val dateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val list = weather.filter { it.dateRange.isOverlaps(weekRange) }
        .groupBy { it.dateTime.date }
        .mapNotNull { entry ->
            val base = if (dateTime.date == entry.key) {
                dateTime
            } else {
                LocalDateTime(entry.key, LocalTime(14, 0))
            }

            entry.value.minByOrNull { abs(it.dateTime.toEpochMilliseconds() - base.toEpochMilliseconds()) }
        }
        .sortedBy { it.dateTime }
        .toMutableList()

    return internalBuildList(
        weekRange = weekRange,
        list = list,
        content = { uiState, span ->
            CalendarWeekItem.Weather(
                icon = uiState.icon,
                temperature = uiState.temperature,
                instant = uiState.instant,
                span = span,
            )
        },
    )
}

private fun textWeekItem(
    weekRange: LocalDateRange,
    text: List<CalendarTextItemUiState>,
): List<CalendarWeekItem> {
    val list = text.filter { it.dateRange.isOverlaps(weekRange) }

    return internalBuildList(
        weekRange = weekRange,
        list = list,
        content = { uiState, span ->
            when (uiState) {
                is CalendarTextItemUiState.ColorText -> {
                    CalendarWeekItem.ColorText(
                        calendarItemKey = uiState.key,
                        text = uiState.text,
                        color = Color(uiState.color),
                        span = span,
                    )
                }

                is CalendarTextItemUiState.Holiday -> {
                    CalendarWeekItem.Holiday(
                        key = uiState.toString(),
                        text = uiState.text,
                        uri = uiState.uri,
                        span = span,
                    )
                }

                is CalendarTextItemUiState.SpecialDay -> {
                    CalendarWeekItem.SpecialDay(
                        key = uiState.toString(),
                        text = uiState.text,
                        uri = uiState.uri,
                        span = span,
                    )
                }
            }
        },
    )
}

private fun <T : CalendarItemUiState> internalBuildList(
    weekRange: LocalDateRange,
    list: List<T>,
    content: (T, Int) -> CalendarWeekItem,
): List<CalendarWeekItem> {
    val mutableList = list.toMutableList()

    return buildList {
        while (mutableList.isNotEmpty()) {
            val iterator = mutableList.iterator()

            var currentDayOfWeek = weekRange.start.dayOfWeek
            var isFull = false
            while (iterator.hasNext()) {
                val uiState = iterator.next()
                val space = dayNumber(maxOf(weekRange.start, uiState.dateRange.start).dayOfWeek, weekRange.start.dayOfWeek) - dayNumber(currentDayOfWeek, weekRange.start.dayOfWeek)

                if (space < 0) continue

                if (space > 0) {
                    add(CalendarWeekItem.Space(space))
                }

                val span = dayNumber(minOf(weekRange.endInclusive, uiState.dateRange.endInclusive).dayOfWeek, weekRange.start.dayOfWeek) - dayNumber(maxOf(weekRange.start, uiState.dateRange.start).dayOfWeek, weekRange.start.dayOfWeek) + 1

                add(content(uiState, span))
                iterator.remove()

                if (minOf(uiState.dateRange.endInclusive, weekRange.endInclusive) == weekRange.endInclusive) {
                    currentDayOfWeek = weekRange.endInclusive.dayOfWeek
                    isFull = true
                    break
                } else {
                    currentDayOfWeek = dayOfWeek(dayNumber(minOf(weekRange.endInclusive, uiState.dateRange.endInclusive).dayOfWeek, weekRange.start.dayOfWeek) + 1, weekRange.start.dayOfWeek)
                }
            }

            if (!isFull) {
                val span = dayNumber(weekRange.endInclusive.dayOfWeek, weekRange.start.dayOfWeek) - dayNumber(currentDayOfWeek, weekRange.start.dayOfWeek) + 1
                add(CalendarWeekItem.Space(span))
            }
        }
    }
}

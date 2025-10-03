package io.github.taetae98coding.diary.compose.calendar.week

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import io.github.taetae98coding.diary.compose.calendar.CalendarDefaults
import io.github.taetae98coding.diary.compose.calendar.color.CalendarColors
import io.github.taetae98coding.diary.compose.calendar.item.CalendarTextItemUiState
import io.github.taetae98coding.diary.compose.calendar.item.CalendarWeatherUiState

@Composable
internal fun CalendarItemGridColumn(
    state: WeekOfMonthState,
    modifier: Modifier = Modifier,
    colorTextProvider: () -> List<CalendarTextItemUiState.ColorText> = { emptyList() },
    weatherProvider: () -> List<CalendarWeatherUiState> = { emptyList() },
    holidayProvider: () -> List<CalendarTextItemUiState.Holiday> = { emptyList() },
    specialDayProvider: () -> List<CalendarTextItemUiState.SpecialDay> = { emptyList() },
    onColorTextClick: ((Any) -> Unit)? = null,
    colors: CalendarColors = CalendarDefaults.colors(),
) {
    val uriHandler = LocalUriHandler.current
    val gridState = rememberLazyGridState()
    val weekItem by remember(state) {
        derivedStateOf {
            combineCalendarWeekItem(
                weekRange = state.dateRange,
                colorTextList = colorTextProvider(),
                weatherList = weatherProvider(),
                holidayList = holidayProvider(),
                specialDayList = specialDayProvider(),
            )
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = modifier,
        state = gridState,
        contentPadding = PaddingValues(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items(
            items = weekItem,
            key = { it.key },
            span = { GridItemSpan(it.span) },
            contentType = { it.contentType },
        ) { item ->
            when (item) {
                is CalendarWeekItem.Weather -> {
                    CalendarWeatherItem(
                        item = item,
                        modifier = Modifier.animateItem(),
                    )
                }

                is CalendarWeekItem.ColorText -> {
                    CalendarDayItem(
                        item = item,
                        modifier = Modifier.animateItem()
                            .let {
                                if (onColorTextClick == null) {
                                    it
                                } else {
                                    it.clickable { onColorTextClick(item.calendarItemKey) }
                                }
                            },
                    )
                }

                is CalendarWeekItem.Holiday -> {
                    CalendarHolidayItem(
                        item = item,
                        modifier = Modifier.animateItem()
                            .clickable { uriHandler.openUri(item.uri) },
                        colors = colors,
                    )
                }

                is CalendarWeekItem.SpecialDay -> {
                    CalendarSpecialDayItem(
                        item = item,
                        modifier = Modifier.animateItem()
                            .clickable { uriHandler.openUri(item.uri) },
                        colors = colors,
                    )
                }

                is CalendarWeekItem.Space -> Unit
            }
        }
    }

    ScrollTopEffect(
        state = gridState,
        itemProvider = { weekItem },
    )
}

@Composable
private fun ScrollTopEffect(
    state: LazyGridState,
    itemProvider: () -> List<CalendarWeekItem>,
) {
    val scrollTop by remember {
        derivedStateOf {
            itemProvider().filterIsInstance<CalendarWeekItem.Weather>()
                .isNotEmpty()
        }
    }

    LaunchedEffect(state, scrollTop) {
        if (!state.canScrollBackward && scrollTop) {
            state.requestScrollToItem(0)
        }
    }
}

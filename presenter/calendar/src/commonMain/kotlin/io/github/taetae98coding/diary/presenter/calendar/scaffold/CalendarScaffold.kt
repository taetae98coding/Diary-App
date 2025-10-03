package io.github.taetae98coding.diary.presenter.calendar.scaffold

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.taetae98coding.diary.compose.calendar.Calendar
import io.github.taetae98coding.diary.compose.calendar.item.CalendarTextItemUiState
import io.github.taetae98coding.diary.compose.calendar.item.CalendarWeatherUiState
import io.github.taetae98coding.diary.compose.core.date.DiaryDatePicker
import io.github.taetae98coding.diary.compose.core.date.rememberDiaryDatePickerState
import io.github.taetae98coding.diary.compose.core.effect.PlatformRefreshLifecycleEffect
import io.github.taetae98coding.diary.compose.core.snackbar.showSnackbarImmediate
import io.github.taetae98coding.diary.library.compose.foundation.shortcutFocus
import io.github.taetae98coding.diary.library.compose.ui.onDirectionLeftShortcut
import io.github.taetae98coding.diary.library.compose.ui.onDirectionRightShortcut
import io.github.taetae98coding.diary.presenter.calendar.colortext.CalendarColorTextEffect
import io.github.taetae98coding.diary.presenter.calendar.colortext.CalendarColorTextStateHolder
import io.github.taetae98coding.diary.presenter.calendar.holiday.CalendarHolidayStateHolder
import io.github.taetae98coding.diary.presenter.calendar.topbar.CalendarTopBar
import io.github.taetae98coding.diary.presenter.calendar.weather.CalendarWeatherStateHolder
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateRange
import kotlinx.datetime.yearMonth

@Composable
public fun CalendarScaffold(
    colorTestStateHolder: CalendarColorTextStateHolder,
    weatherStateHolder: CalendarWeatherStateHolder,
    holidayStateHolder: CalendarHolidayStateHolder,
    modifier: Modifier = Modifier,
    state: CalendarScaffoldState = rememberCalendarScaffoldState(),
    onDrag: ((LocalDateRange) -> Unit)? = null,
    onColorTextClick: ((Any) -> Unit)? = null,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()

    val colorText by colorTestStateHolder.colorTextUiState.collectAsStateWithLifecycle()
    val weather by weatherStateHolder.weatherUiState.collectAsStateWithLifecycle()
    val holiday by holidayStateHolder.holidayUiState.collectAsStateWithLifecycle()
    val specialDay by holidayStateHolder.specialDayUiState.collectAsStateWithLifecycle()

    CalendarScaffold(
        state = state,
        colorTextProvider = { colorText },
        weatherProvider = { weather },
        holidayProvider = { holiday },
        specialDayProvider = { specialDay },
        modifier = modifier.shortcutFocus(state.focusRequester)
            .onDirectionLeftShortcut { coroutineScope.launch { state.calendarState.animateScrollToPreviousPage() } }
            .onDirectionRightShortcut { coroutineScope.launch { state.calendarState.animateScrollToNextPage() } },
        onDrag = onDrag,
        onColorTextClick = onColorTextClick,
        navigationIcon = navigationIcon,
        actions = actions,
    )

    DatePickerHost(state = state)

    UpdateTodayEffect(state = state)

    ShortcutFocusEffect(state = state)

    FetchColorTextEffect(
        state = state,
        stateHolder = colorTestStateHolder,
    )

    FetchWeatherEffect(
        stateHolder = weatherStateHolder,
    )

    FetchHolidayEffect(
        state = state,
        stateHolder = holidayStateHolder,
    )

    CalendarScaffoldColorTextEffect(
        state = state,
        stateHolder = colorTestStateHolder,
    )
}

@Composable
private fun CalendarScaffold(
    state: CalendarScaffoldState,
    colorTextProvider: () -> List<CalendarTextItemUiState.ColorText>,
    weatherProvider: () -> List<CalendarWeatherUiState>,
    holidayProvider: () -> List<CalendarTextItemUiState.Holiday>,
    specialDayProvider: () -> List<CalendarTextItemUiState.SpecialDay>,
    modifier: Modifier = Modifier,
    onDrag: ((LocalDateRange) -> Unit)? = null,
    onColorTextClick: ((Any) -> Unit)? = null,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CalendarTopBar(
                state = state,
                navigationIcon = navigationIcon,
                actions = actions,
            )
        },
        snackbarHost = { SnackbarHost(hostState = state.hostState) },
    ) {
        Calendar(
            state = state.calendarState,
            modifier = Modifier.padding(it),
            primaryDateProvider = { listOf(state.today) },
            colorTextProvider = colorTextProvider,
            weatherProvider = weatherProvider,
            holidayProvider = holidayProvider,
            specialDayProvider = specialDayProvider,
            onDrag = onDrag,
            onColorTextClick = onColorTextClick,
        )
    }
}

@Composable
private fun DatePickerHost(
    state: CalendarScaffoldState,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    if (state.isDatePickerVisible) {
        val datePickerState = rememberDiaryDatePickerState(initialSelectedLocalDate = state.calendarState.yearMonth.firstDay)

        DatePickerDialog(
            onDismissRequest = { state.hideDatePicker() },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedLocalDate?.let {
                            coroutineScope.launch { state.calendarState.animateScrollTo(it.yearMonth) }
                            state.hideDatePicker()
                        }
                    },
                    enabled = datePickerState.selectedLocalDate != null,
                ) {
                    Text(text = "확인")
                }
            },
            modifier = modifier,
        ) {
            DiaryDatePicker(
                state = datePickerState,
            )
        }
    }
}

@Composable
private fun UpdateTodayEffect(
    state: CalendarScaffoldState,
) {
    PlatformRefreshLifecycleEffect(state) {
        state.refreshToday()
    }
}

@Composable
private fun ShortcutFocusEffect(
    state: CalendarScaffoldState,
) {
    LifecycleResumeEffect(state.focusRequester) {
        state.focusRequester.requestFocus()
        onPauseOrDispose { }
    }
}

@Composable
private fun FetchColorTextEffect(
    state: CalendarScaffoldState,
    stateHolder: CalendarColorTextStateHolder,
) {
    PlatformRefreshLifecycleEffect(stateHolder, state.calendarState.yearMonth) {
        stateHolder.fetchColorText(state.calendarState.yearMonth)
    }
}

@Composable
private fun FetchWeatherEffect(
    stateHolder: CalendarWeatherStateHolder,
) {
    PlatformRefreshLifecycleEffect(stateHolder) {
        stateHolder.fetchWeather()
    }
}

@Composable
private fun FetchHolidayEffect(
    state: CalendarScaffoldState,
    stateHolder: CalendarHolidayStateHolder,
) {
    PlatformRefreshLifecycleEffect(stateHolder, state.calendarState.yearMonth) {
        stateHolder.fetchHoliday(state.calendarState.yearMonth)
    }
}

@Composable
private fun CalendarScaffoldColorTextEffect(
    state: CalendarScaffoldState,
    stateHolder: CalendarColorTextStateHolder,
) {
    val coroutineScope = rememberCoroutineScope()
    val effect by stateHolder.effect.collectAsStateWithLifecycle()

    LaunchedEffect(state, stateHolder, effect) {
        when (effect) {
            is CalendarColorTextEffect.None -> Unit

            is CalendarColorTextEffect.FetchError -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("오프라인 상태입니다") }
                stateHolder.clearEffect()
            }

            is CalendarColorTextEffect.UnknownError -> {
                coroutineScope.launch { state.hostState.showSnackbarImmediate("네트워크 상태를 확인하세요") }
                stateHolder.clearEffect()
            }
        }
    }
}

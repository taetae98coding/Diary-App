package io.github.taetae98coding.diary.feature.calendar

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.calendar.colortext.CalendarColorTextStateHolder
import io.github.taetae98coding.diary.presenter.calendar.colortext.CalendarColorTextStateHolderTemplate
import io.github.taetae98coding.diary.presenter.calendar.holiday.CalendarHolidayStateHolder
import io.github.taetae98coding.diary.presenter.calendar.holiday.CalendarHolidayStateHolderTemplate
import io.github.taetae98coding.diary.presenter.calendar.weather.CalendarWeatherStateHolder
import io.github.taetae98coding.diary.presenter.calendar.weather.CalendarWeatherStateHolderTemplate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class CalendarViewModel(
    private val colorTextStrategy: AccountCalendarColorTextStrategy,
    private val colorTextStateHolder: CalendarColorTextStateHolderTemplate = CalendarColorTextStateHolderTemplate(strategy = colorTextStrategy),
    private val weatherStateHolder: CalendarWeatherStateHolderTemplate,
    private val holidayStateHolder: CalendarHolidayStateHolderTemplate,
) : ViewModel(
    colorTextStateHolder,
    weatherStateHolder,
    holidayStateHolder,
),
    CalendarColorTextStateHolder by colorTextStateHolder,
    CalendarWeatherStateHolder by weatherStateHolder,
    CalendarHolidayStateHolder by holidayStateHolder

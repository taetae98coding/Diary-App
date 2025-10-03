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
    colorTextStrategy: AccountCalendarColorTextStrategy,
    colorTextStateHolder: CalendarColorTextStateHolderTemplate = CalendarColorTextStateHolderTemplate(strategy = colorTextStrategy),
    weatherStateHolder: CalendarWeatherStateHolderTemplate,
    holidayStateHolder: CalendarHolidayStateHolderTemplate,
) : ViewModel(
    colorTextStateHolder,
    weatherStateHolder,
    holidayStateHolder,
),
    CalendarColorTextStateHolder by colorTextStateHolder,
    CalendarWeatherStateHolder by weatherStateHolder,
    CalendarHolidayStateHolder by holidayStateHolder

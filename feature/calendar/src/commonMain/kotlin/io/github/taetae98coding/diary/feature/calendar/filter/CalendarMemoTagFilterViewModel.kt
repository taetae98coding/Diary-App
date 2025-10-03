package io.github.taetae98coding.diary.feature.calendar.filter

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.calendar.filter.CalendarFilterStateHolder
import io.github.taetae98coding.diary.presenter.calendar.filter.CalendarFilterStateHolderTemplate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class CalendarMemoTagFilterViewModel(
    strategy: AccountCalendarFilterStrategy,
    stateHolder: CalendarFilterStateHolderTemplate = CalendarFilterStateHolderTemplate(strategy = strategy),
) : ViewModel(stateHolder),
    CalendarFilterStateHolder by stateHolder

package io.github.taetae98coding.diary.feature.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.taetae98coding.diary.domain.calendar.usecase.HasMemoCalendarFilterUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class CalendarFilterViewModel(
    hasMemoCalendarFilterUseCase: HasMemoCalendarFilterUseCase,
) : ViewModel() {
    val hasFilter = hasMemoCalendarFilterUseCase().mapLatest { it.getOrNull() ?: false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )
}

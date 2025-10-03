package io.github.taetae98coding.diary.presenter.calendar.colortext

import io.github.taetae98coding.diary.compose.calendar.item.CalendarTextItemUiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.YearMonth

public interface CalendarColorTextStateHolder {
    public val colorTextUiState: StateFlow<List<CalendarTextItemUiState.ColorText>>
    public val effect: StateFlow<CalendarColorTextEffect>

    public fun fetchColorText(yearMonth: YearMonth)
    public fun clearEffect()
}

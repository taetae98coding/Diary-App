package io.github.taetae98coding.diary.presenter.calendar.weather

import io.github.taetae98coding.diary.compose.calendar.item.CalendarWeatherUiState
import kotlinx.coroutines.flow.StateFlow

public interface CalendarWeatherStateHolder {
    public val weatherUiState: StateFlow<List<CalendarWeatherUiState>>

    public fun fetchWeather()
}

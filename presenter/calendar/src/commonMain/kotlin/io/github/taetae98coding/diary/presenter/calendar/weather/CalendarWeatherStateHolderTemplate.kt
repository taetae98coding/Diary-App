package io.github.taetae98coding.diary.presenter.calendar.weather

import io.github.taetae98coding.diary.compose.calendar.item.CalendarWeatherIconUiState
import io.github.taetae98coding.diary.compose.calendar.item.CalendarWeatherUiState
import io.github.taetae98coding.diary.core.coroutines.ext.MainImmediateScope
import io.github.taetae98coding.diary.domain.weather.usecase.GetWeatherUseCase
import io.github.taetae98coding.diary.library.kotlinx.coroutines.core.flow.mapCollectionLatest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory
public class CalendarWeatherStateHolderTemplate(
    getWeatherUseCase: GetWeatherUseCase,
    private val coroutineScope: CoroutineScope = MainImmediateScope(),
) : CalendarWeatherStateHolder,
    AutoCloseable {
    private val fetchCountFlow = MutableStateFlow(0)

    override val weatherUiState: StateFlow<List<CalendarWeatherUiState>> = fetchCountFlow.flatMapLatest { fetchFount ->
        if (fetchFount == 0) {
            flowOf(emptyList())
        } else {
            getWeatherUseCase().mapLatest { it.getOrNull() }
                .filterNotNull()
        }
    }.mapCollectionLatest { weather ->
        CalendarWeatherUiState(
            icon = weather.type.map {
                CalendarWeatherIconUiState(
                    icon = it.icon,
                    description = it.description,
                )
            },
            temperature = weather.temperature,
            instant = weather.instant,
        )
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList(),
    )

    override fun fetchWeather() {
        coroutineScope.launch {
            fetchCountFlow.emit(fetchCountFlow.value + 1)
        }
    }

    override fun close() {
        coroutineScope.cancel()
    }
}

package io.github.taetae98coding.diary.presenter.calendar.holiday

import io.github.taetae98coding.diary.compose.calendar.item.CalendarTextItemUiState
import io.github.taetae98coding.diary.core.coroutines.ext.MainImmediateScope
import io.github.taetae98coding.diary.domain.holiday.usecase.FetchHolidayUseCase
import io.github.taetae98coding.diary.domain.holiday.usecase.FetchSpecialDayUseCase
import io.github.taetae98coding.diary.domain.holiday.usecase.GetHolidayUseCase
import io.github.taetae98coding.diary.domain.holiday.usecase.GetSpecialDayUseCase
import io.github.taetae98coding.diary.library.kotlinx.coroutines.core.flow.combine
import io.github.taetae98coding.diary.library.kotlinx.coroutines.core.flow.mapCollectionLatest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.YearMonth
import kotlinx.datetime.plus
import org.koin.core.annotation.Factory

@Factory
public class CalendarHolidayStateHolderTemplate(
    private val fetchHolidayUseCase: FetchHolidayUseCase,
    private val fetchSpecialDayUseCase: FetchSpecialDayUseCase,
    getHolidayUseCase: GetHolidayUseCase,
    getSpecialDayUseCase: GetSpecialDayUseCase,
    private val coroutineScope: CoroutineScope = MainImmediateScope(),
) : CalendarHolidayStateHolder,
    AutoCloseable {

    private val yearMonthFlow = MutableStateFlow<YearMonth?>(null)
    private val yearMonthList = yearMonthFlow.mapLatest { yearMonth ->
        if (yearMonth == null) {
            null
        } else {
            IntRange(-2, 2).map { yearMonth.plus(it, DateTimeUnit.MONTH) }
        }
    }

    override val holidayUiState: StateFlow<List<CalendarTextItemUiState.Holiday>> = yearMonthList.flatMapLatest { list ->
        list.orEmpty().map { getHolidayUseCase(it) }
            .combine { array -> array.flatMap { it.getOrNull().orEmpty() } }
            .mapCollectionLatest {
                CalendarTextItemUiState.Holiday(
                    text = it.name,
                    uri = it.uri,
                    dateRange = it.dateRange,
                )
            }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList(),
    )

    override val specialDayUiState: StateFlow<List<CalendarTextItemUiState.SpecialDay>> = yearMonthList.flatMapLatest { list ->
        list.orEmpty().map { getSpecialDayUseCase(it) }
            .combine { array -> array.flatMap { it.getOrNull().orEmpty() } }
            .mapCollectionLatest {
                CalendarTextItemUiState.SpecialDay(
                    text = it.name,
                    uri = it.uri,
                    dateRange = it.dateRange,
                )
            }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList(),
    )

    override fun fetchHoliday(yearMonth: YearMonth) {
        coroutineScope.launch {
            IntRange(-2, 2).map { yearMonth.plus(it, DateTimeUnit.MONTH) }
                .forEach {
                    launch { fetchHolidayUseCase(it) }
                    launch { fetchSpecialDayUseCase(it) }
                }

            yearMonthFlow.emit(yearMonth)
        }
    }

    override fun close() {
        coroutineScope.cancel()
    }
}

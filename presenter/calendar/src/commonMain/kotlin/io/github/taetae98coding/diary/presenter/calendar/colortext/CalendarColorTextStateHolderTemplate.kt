package io.github.taetae98coding.diary.presenter.calendar.colortext

import io.github.taetae98coding.diary.compose.calendar.item.CalendarTextItemUiState
import io.github.taetae98coding.diary.core.coroutines.ext.MainImmediateScope
import io.github.taetae98coding.diary.core.logger.Logger
import io.github.taetae98coding.diary.library.kotlinx.coroutines.core.flow.mapCollectionLatest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateRange
import kotlinx.datetime.YearMonth
import kotlinx.datetime.minus
import kotlinx.datetime.plus

public class CalendarColorTextStateHolderTemplate(
    private val strategy: CalendarColorTextStrategy,
    private val coroutineScope: CoroutineScope = MainImmediateScope(),
) : CalendarColorTextStateHolder,
    AutoCloseable {
    private val dayItemYearMonth = MutableStateFlow<YearMonth?>(null)

    override val colorTextUiState: StateFlow<List<CalendarTextItemUiState.ColorText>> = dayItemYearMonth.flatMapLatest { yearMonth ->
        if (yearMonth == null) {
            flowOf(emptyList())
        } else {
            strategy.getCalendarMemo(yearMonth.calendarRange()).mapLatest { it.getOrNull() }
                .filterNotNull()
                .mapCollectionLatest {
                    CalendarTextItemUiState.ColorText(
                        key = MemoKey(it.id),
                        text = it.title,
                        dateRange = it.dateRange,
                        color = it.color,
                    )
                }
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList(),
    )

    private val _effect: MutableStateFlow<CalendarColorTextEffect> = MutableStateFlow(CalendarColorTextEffect.None)
    override val effect: StateFlow<CalendarColorTextEffect> = _effect.asStateFlow()

    override fun fetchColorText(yearMonth: YearMonth) {
        coroutineScope.launch {
            dayItemYearMonth.emit(yearMonth)
            strategy.fetchCalendarMemo(yearMonth.calendarRange())
                .onFailure {
                    Logger.log("[CalendarColorTextStateHolderTemplate] 캘린더 메모 패치 실패 : $it")
                    _effect.emit(CalendarColorTextEffect.FetchError)
                }
        }
    }

    override fun clearEffect() {
        coroutineScope.launch {
            _effect.emit(CalendarColorTextEffect.None)
        }
    }

    override fun close() {
        coroutineScope.cancel()
    }

    private fun YearMonth.calendarRange(): LocalDateRange {
        val start = minus(2, DateTimeUnit.MONTH).firstDay
        val endInclusive = plus(2, DateTimeUnit.MONTH).lastDay

        return start..endInclusive
    }
}

package io.github.taetae98coding.diary.feature.more.holiday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.taetae98coding.diary.compose.calendar.item.CalendarTextItemUiState
import io.github.taetae98coding.diary.core.coroutines.di.IoDispatcher
import io.github.taetae98coding.diary.core.entity.holiday.Holiday
import io.github.taetae98coding.diary.domain.holiday.usecase.FetchHolidayUseCase
import io.github.taetae98coding.diary.domain.holiday.usecase.GetHolidayUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateRange
import kotlinx.datetime.YearMonth
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.yearMonth
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class GoldHolidayViewModel(
    private val fetchHolidayUseCase: FetchHolidayUseCase,
    private val getHolidayUseCase: GetHolidayUseCase,
    @param:IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState = MutableStateFlow<GoldHolidayUiState>(GoldHolidayUiState.FetchProgress(0F))
    val uiState = _uiState.asStateFlow()

    private var year = 0
    private var vacationCount = 0
    private var fetchJob: Job? = null

    fun fetch(year: Int, vacationCount: Int) {
        if (year == this.year && vacationCount == this.vacationCount) return

        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                fetchHoliday(year)
                calculate(year, vacationCount)
            } catch (_: Throwable) {
                _uiState.emit(GoldHolidayUiState.Error)
            }
        }
    }

    private suspend fun fetchHoliday(year: Int) {
        _uiState.emit(GoldHolidayUiState.FetchProgress())

        val mutex = Mutex()
        var count = 0

        coroutineScope {
            range(year).map {
                async {
                    fetchHolidayUseCase(it)
                        .also { mutex.withLock { count++ } }
                }
            }.onEach {
                it.invokeOnCompletion {
                    _uiState.update { GoldHolidayUiState.FetchProgress(1F * count / RANGE_SIZE) }
                }
            }.awaitAll()
        }.forEach {
            it.getOrThrow()
        }
    }

    private suspend fun calculate(year: Int, vacationCount: Int) {
        val start = LocalDate(year, 1, 1)
        val endInclusive = start.plus(1, DateTimeUnit.YEAR)
            .minus(1, DateTimeUnit.DAY)
        val holiday = coroutineScope {
            range(year).map { async { getHolidayUseCase(it).first().getOrThrow() } }
                .awaitAll()
                .flatten()
        }

        val yearMonthUiState = coroutineScope {
            (start..endInclusive).map { async { calculate(it, holiday, vacationCount) } }
                .awaitAll()
                .sortedByDescending { it.goldHoliday }
                .distinctBy { it.holiday }
                .filter { it.holiday.isNotEmpty() }
                .filter { it.goldHoliday > 2 }
        }

        _uiState.emit(GoldHolidayUiState.Result(yearMonthUiState = yearMonthUiState))
    }

    private fun range(year: Int): List<YearMonth> {
        return (YearMonth(year - 1, 12)..YearMonth(year + 1, 1)).toList()
    }

    private suspend fun calculate(startLocalDate: LocalDate, holiday: List<Holiday>, vacationCount: Int): GoldHolidayYearMonthUiState {
        return withContext(ioDispatcher) {
            var currentLocalDate = startLocalDate
            var currentVacationCount = vacationCount

            val annualLeave = mutableListOf<LocalDateRange>()
            val holidaySet = mutableSetOf<CalendarTextItemUiState.Holiday>()
            var goldHoliday = 0

            while (true) {
                val beforeLocalDate = currentLocalDate.minus(1, DateTimeUnit.DAY)
                if (beforeLocalDate.isRedDay(holiday)) {
                    currentLocalDate = beforeLocalDate
                } else {
                    break
                }
            }

            while (true) {
                holiday.forEach {
                    if (currentLocalDate in it.dateRange) {
                        holidaySet.add(
                            CalendarTextItemUiState.Holiday(
                                text = it.name,
                                uri = it.uri,
                                dateRange = it.dateRange,
                            ),
                        )
                    }
                }

                if (!currentLocalDate.isRedDay(holiday)) {
                    if (currentVacationCount > 0) {
                        currentVacationCount--
                        if (annualLeave.isEmpty()) {
                            annualLeave.add(currentLocalDate..currentLocalDate)
                        } else {
                            val last = annualLeave.last().endInclusive
                            if (last.plus(1, DateTimeUnit.DAY) == currentLocalDate) {
                                annualLeave[annualLeave.lastIndex] = annualLeave.last().first..currentLocalDate
                            } else {
                                annualLeave.add(currentLocalDate..currentLocalDate)
                            }
                        }
                    } else {
                        break
                    }
                }

                goldHoliday++
                currentLocalDate = currentLocalDate.plus(1, DateTimeUnit.DAY)
            }

            GoldHolidayYearMonthUiState(
                yearMonth = startLocalDate.yearMonth,
                annualLeave = annualLeave,
                holiday = holidaySet.toList().sorted(),
                goldHoliday = goldHoliday,
            )
        }
    }

    private fun LocalDate.isRedDay(holiday: List<Holiday>): Boolean {
        return dayOfWeek.isWeekend() || holiday.any { this in it.dateRange }
    }

    companion object {
        private const val RANGE_SIZE = 14
    }
}

private fun DayOfWeek.isWeekend(): Boolean {
    return this == DayOfWeek.SATURDAY || this == DayOfWeek.SUNDAY
}

package io.github.taetae98coding.diary.feature.calendar

import io.github.taetae98coding.diary.core.entity.memo.CalendarMemo
import io.github.taetae98coding.diary.domain.calendar.usecase.GetCalendarMemoUseCase
import io.github.taetae98coding.diary.presenter.calendar.colortext.CalendarColorTextStrategy
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateRange
import org.koin.core.annotation.Factory

@Factory
internal class AccountCalendarColorTextStrategy(
    private val getCalendarMemoUseCase: GetCalendarMemoUseCase,
) : CalendarColorTextStrategy {
    override suspend fun fetchCalendarMemo(dateRange: LocalDateRange): Result<Unit> {
        return Result.success(Unit)
    }

    override fun getCalendarMemo(dateRange: LocalDateRange): Flow<Result<List<CalendarMemo>>> {
        return getCalendarMemoUseCase(dateRange)
    }
}

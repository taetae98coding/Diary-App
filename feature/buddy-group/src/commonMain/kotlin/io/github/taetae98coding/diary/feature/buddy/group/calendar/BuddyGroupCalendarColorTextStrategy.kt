package io.github.taetae98coding.diary.feature.buddy.group.calendar

import io.github.taetae98coding.diary.core.entity.memo.CalendarMemo
import io.github.taetae98coding.diary.domain.buddy.group.usecase.calendar.FetchBuddyGroupCalendarMemoUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.calendar.GetBuddyGroupCalendarMemoUseCase
import io.github.taetae98coding.diary.presenter.calendar.colortext.CalendarColorTextStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateRange
import kotlinx.datetime.YearMonth
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupCalendarColorTextStrategy(
    private val buddyGroupId: Uuid,
    private val getBuddyGroupCalendarMemoUseCase: GetBuddyGroupCalendarMemoUseCase,
    private val fetchBuddyGroupCalendarMemoUseCase: FetchBuddyGroupCalendarMemoUseCase,
) : CalendarColorTextStrategy {

    override fun getCalendarMemo(dateRange: LocalDateRange): Flow<Result<List<CalendarMemo>>> {
        return getBuddyGroupCalendarMemoUseCase(buddyGroupId, dateRange)
    }

    override suspend fun fetchCalendarMemo(yearMonth: YearMonth): Result<Unit> {
        val start = yearMonth.minus(2, DateTimeUnit.MONTH).firstDay
        val endInclusive = yearMonth.plus(2, DateTimeUnit.MONTH).lastDay

        return fetchBuddyGroupCalendarMemoUseCase(buddyGroupId, start..endInclusive)
    }
}

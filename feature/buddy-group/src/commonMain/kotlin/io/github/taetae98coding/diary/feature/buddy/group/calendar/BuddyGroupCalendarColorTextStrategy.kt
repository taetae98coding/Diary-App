package io.github.taetae98coding.diary.feature.buddy.group.calendar

import io.github.taetae98coding.diary.core.entity.memo.CalendarMemo
import io.github.taetae98coding.diary.core.navigation.parameter.BuddyGroupId
import io.github.taetae98coding.diary.domain.buddy.group.usecase.FetchBuddyGroupDiaryUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.calendar.GetBuddyGroupCalendarMemoUseCase
import io.github.taetae98coding.diary.presenter.calendar.colortext.CalendarColorTextStrategy
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateRange
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupCalendarColorTextStrategy(
    private val buddyGroupId: BuddyGroupId,
    private val fetchBuddyGroupDiaryUseCase: FetchBuddyGroupDiaryUseCase,
    private val getBuddyGroupCalendarMemoUseCase: GetBuddyGroupCalendarMemoUseCase,
) : CalendarColorTextStrategy {
    override suspend fun fetchCalendarMemo(dateRange: LocalDateRange): Result<Unit> {
        return fetchBuddyGroupDiaryUseCase(buddyGroupId.value)
    }

    override fun getCalendarMemo(dateRange: LocalDateRange): Flow<Result<List<CalendarMemo>>> {
        return getBuddyGroupCalendarMemoUseCase(buddyGroupId.value, dateRange)
    }
}

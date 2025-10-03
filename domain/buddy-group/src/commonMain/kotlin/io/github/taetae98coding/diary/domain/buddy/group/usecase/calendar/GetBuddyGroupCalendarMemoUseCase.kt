package io.github.taetae98coding.diary.domain.buddy.group.usecase.calendar

import io.github.taetae98coding.diary.core.entity.memo.CalendarMemo
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoCalendarRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.datetime.LocalDateRange
import org.koin.core.annotation.Factory

@Factory
public class GetBuddyGroupCalendarMemoUseCase internal constructor(
    private val buddyGroupMemoCalendarRepository: BuddyGroupMemoCalendarRepository,
) {
    public operator fun invoke(buddyGroupId: Uuid, dateRange: LocalDateRange): Flow<Result<List<CalendarMemo>>> {
        return flow { emitAll(buddyGroupMemoCalendarRepository.get(buddyGroupId, dateRange)) }
            .mapLatest { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}

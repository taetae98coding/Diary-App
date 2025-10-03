package io.github.taetae98coding.diary.domain.buddy.group.repository

import io.github.taetae98coding.diary.core.entity.memo.CalendarMemo
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateRange

public interface BuddyGroupMemoCalendarRepository {
    public fun get(buddyGroupId: Uuid, dateRange: LocalDateRange): Flow<List<CalendarMemo>>
}

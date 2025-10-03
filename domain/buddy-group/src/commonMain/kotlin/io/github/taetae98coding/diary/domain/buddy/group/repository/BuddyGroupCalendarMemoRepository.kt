package io.github.taetae98coding.diary.domain.buddy.group.repository

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.CalendarMemo
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateRange

public interface BuddyGroupCalendarMemoRepository {
    public fun get(buddyGroupId: Uuid, dateRange: LocalDateRange): Flow<List<CalendarMemo>>
    public suspend fun fetch(account: Account.User, buddyGroupId: Uuid, dateRange: LocalDateRange)
}

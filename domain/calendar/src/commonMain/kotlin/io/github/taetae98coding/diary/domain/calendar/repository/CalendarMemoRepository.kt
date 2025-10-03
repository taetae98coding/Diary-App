package io.github.taetae98coding.diary.domain.calendar.repository

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.CalendarMemo
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateRange

public interface CalendarMemoRepository {
    public fun get(account: Account, dateRange: LocalDateRange): Flow<List<CalendarMemo>>
}

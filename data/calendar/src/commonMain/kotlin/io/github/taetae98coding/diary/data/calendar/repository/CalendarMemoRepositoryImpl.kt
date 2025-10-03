package io.github.taetae98coding.diary.data.calendar.repository

import io.github.taetae98coding.diary.core.database.datasource.MemoCalendarLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.CalendarMemoLocalEntity
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.CalendarMemo
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.calendar.repository.CalendarMemoRepository
import io.github.taetae98coding.diary.library.kotlinx.coroutines.core.flow.mapCollectionLatest
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateRange
import org.koin.core.annotation.Factory

@Factory
internal class CalendarMemoRepositoryImpl(
    private val memoCalendarLocalDataSource: MemoCalendarLocalDataSource,
) : CalendarMemoRepository {
    override fun get(account: Account, dateRange: LocalDateRange): Flow<List<CalendarMemo>> {
        return memoCalendarLocalDataSource.get(account.id, dateRange)
            .mapCollectionLatest(CalendarMemoLocalEntity::toEntity)
    }
}

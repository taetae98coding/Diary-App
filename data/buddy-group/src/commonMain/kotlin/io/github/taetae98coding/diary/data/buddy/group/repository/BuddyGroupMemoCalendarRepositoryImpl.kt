package io.github.taetae98coding.diary.data.buddy.group.repository

import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupMemoCalendarLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.CalendarMemoLocalEntity
import io.github.taetae98coding.diary.core.entity.memo.CalendarMemo
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoCalendarRepository
import io.github.taetae98coding.diary.library.kotlinx.coroutines.core.flow.mapCollectionLatest
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateRange
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupMemoCalendarRepositoryImpl(
    private val calendarMemoLocalDataSource: BuddyGroupMemoCalendarLocalDataSource,
) : BuddyGroupMemoCalendarRepository {

    override fun get(buddyGroupId: Uuid, dateRange: LocalDateRange): Flow<List<CalendarMemo>> {
        return calendarMemoLocalDataSource.get(buddyGroupId, dateRange)
            .mapCollectionLatest(CalendarMemoLocalEntity::toEntity)
    }
}

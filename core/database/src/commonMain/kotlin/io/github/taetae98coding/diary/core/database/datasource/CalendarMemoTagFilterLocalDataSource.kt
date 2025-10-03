package io.github.taetae98coding.diary.core.database.datasource

import androidx.paging.PagingSource
import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.CalendarMemoTagFilterLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoTagFilterLocalEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
public class CalendarMemoTagFilterLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public suspend fun upsert(entity: Collection<CalendarMemoTagFilterLocalEntity>) {
        database.calendarMemoTagFilter().upsert(entity)
    }

    public fun hasFilter(accountId: Uuid): Flow<Boolean> {
        return database.calendarMemoTagFilter().hasFilter(accountId)
    }

    public fun page(accountId: Uuid): PagingSource<Int, MemoTagFilterLocalEntity> {
        return database.calendarMemoTagFilter().page(accountId)
    }
}

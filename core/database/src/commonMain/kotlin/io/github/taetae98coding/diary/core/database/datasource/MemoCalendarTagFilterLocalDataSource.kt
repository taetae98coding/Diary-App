package io.github.taetae98coding.diary.core.database.datasource

import androidx.paging.PagingSource
import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.MemoCalendarTagFilterLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagFilterLocalEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
public class MemoCalendarTagFilterLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public suspend fun upsert(entity: Collection<MemoCalendarTagFilterLocalEntity>) {
        database.memoCalendarTagFilter().upsert(entity)
    }

    public fun hasFilter(accountId: Uuid): Flow<Boolean> {
        return database.memoCalendarTagFilter().hasFilter(accountId)
    }

    public fun page(accountId: Uuid): PagingSource<Int, TagFilterLocalEntity> {
        return database.memoCalendarTagFilter().page(accountId)
    }
}

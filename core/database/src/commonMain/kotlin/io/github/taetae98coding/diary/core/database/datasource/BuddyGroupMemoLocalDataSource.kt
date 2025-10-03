package io.github.taetae98coding.diary.core.database.datasource

import androidx.paging.PagingSource
import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupMemoLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import kotlin.uuid.Uuid
import kotlinx.datetime.LocalDateRange
import org.koin.core.annotation.Factory

@Factory
public class BuddyGroupMemoLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public suspend fun upsert(entity: Collection<BuddyGroupMemoLocalEntity>) {
        database.buddyGroupMemo().upsert(entity)
    }

    public suspend fun deleteBydDateRange(buddyGroupId: Uuid, dateRange: LocalDateRange) {
        database.buddyGroupMemo().deleteByDateRange(buddyGroupId, dateRange.start, dateRange.endInclusive)
    }

    public suspend fun delete(buddyGroupId: Uuid) {
        database.buddyGroupMemo().delete(buddyGroupId)
    }

    public fun page(buddyGroupId: Uuid): PagingSource<Int, MemoLocalEntity> {
        return database.buddyGroupMemo().page(buddyGroupId)
    }
}

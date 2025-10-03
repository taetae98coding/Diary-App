package io.github.taetae98coding.diary.core.database.datasource

import androidx.paging.PagingSource
import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.MemoListTagFilterLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagFilterLocalEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
public class MemoListTagFilterLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public suspend fun upsert(entity: Collection<MemoListTagFilterLocalEntity>) {
        database.memoListTagFilter().upsert(entity)
    }

    public fun hasFilter(accountId: Uuid): Flow<Boolean> {
        return database.memoListTagFilter().hasFilter(accountId)
    }

    public fun page(accountId: Uuid): PagingSource<Int, TagFilterLocalEntity> {
        return database.memoListTagFilter().page(accountId)
    }
}

package io.github.taetae98coding.diary.core.database.datasource

import androidx.paging.PagingSource
import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.ListMemoTagFilterLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoTagFilterLocalEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
public class ListMemoTagFilterLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {
    public suspend fun upsert(entity: Collection<ListMemoTagFilterLocalEntity>) {
        database.listMemoTagFilter().upsert(entity)
    }

    public fun hasFilter(accountId: Uuid): Flow<Boolean> {
        return database.listMemoTagFilter().hasFilter(accountId)
    }

    public fun page(accountId: Uuid): PagingSource<Int, MemoTagFilterLocalEntity> {
        return database.listMemoTagFilter().page(accountId)
    }
}

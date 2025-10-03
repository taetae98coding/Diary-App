package io.github.taetae98coding.diary.core.database.datasource

import androidx.paging.PagingSource
import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.AccountTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
public class AccountTagLocalDataSource internal constructor(
    private val database: DiaryDatabase,
    private val transactor: DatabaseTransactor,
) {
    public suspend fun upsert(accountId: Uuid, entity: Collection<TagLocalEntity>) {
        transactor.immediate {
            database.tag().upsert(entity)
            database.accountTag().upsert(entity.map { AccountTagLocalEntity(accountId, it.id) })
        }
    }

    public fun page(accountId: Uuid): PagingSource<Int, TagLocalEntity> {
        return database.accountTag().page(accountId)
    }

    public fun getLastUpdatedAt(accountId: Uuid): Flow<Long?> {
        return database.accountTag().getLastUpdatedAt(accountId)
    }
}

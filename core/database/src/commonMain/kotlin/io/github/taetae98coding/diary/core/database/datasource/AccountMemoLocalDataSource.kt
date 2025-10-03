package io.github.taetae98coding.diary.core.database.datasource

import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.AccountMemoLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
public class AccountMemoLocalDataSource internal constructor(
    private val database: DiaryDatabase,
    private val transactor: DatabaseTransactor,
) {
    public suspend fun upsert(accountId: Uuid, entity: List<MemoLocalEntity>) {
        transactor.immediate {
            database.memo().upsert(entity)
            database.accountMemo().upsert(entity.map { AccountMemoLocalEntity(accountId, it.id) })
        }
    }

    public fun getLastUpdatedAt(accountId: Uuid): Flow<Long?> {
        return database.accountMemo().getLastUpdatedAt(accountId)
    }
}

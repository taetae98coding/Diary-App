package io.github.taetae98coding.diary.core.database.transaction.account

import io.github.taetae98coding.diary.core.database.datasource.AccountMemoLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.AccountMemoLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class AccountMemoTransaction internal constructor(
    private val transactor: DatabaseTransactor,
    private val memoLocalDataSource: MemoLocalDataSource,
    private val accountMemoLocalDataSource: AccountMemoLocalDataSource,
) {
    public suspend fun upsert(accountId: Uuid, entity: List<MemoLocalEntity>) {
        transactor.transaction {
            memoLocalDataSource.upsert(entity)
            accountMemoLocalDataSource.upsert(entity.map { AccountMemoLocalEntity(accountId, it.id) })
        }
    }
}

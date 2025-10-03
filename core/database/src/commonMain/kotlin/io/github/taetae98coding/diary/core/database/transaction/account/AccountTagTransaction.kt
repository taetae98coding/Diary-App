package io.github.taetae98coding.diary.core.database.transaction.account

import io.github.taetae98coding.diary.core.database.datasource.AccountTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.TagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.AccountTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class AccountTagTransaction internal constructor(
    private val transactor: DatabaseTransactor,
    private val tagLocalDataSource: TagLocalDataSource,
    private val accountTagLocalDataSource: AccountTagLocalDataSource,
) {
    public suspend fun upsert(accountId: Uuid, entity: List<TagLocalEntity>) {
        transactor.transaction {
            tagLocalDataSource.upsert(entity)
            accountTagLocalDataSource.upsert(entity.map { AccountTagLocalEntity(accountId, it.id) })
        }
    }
}

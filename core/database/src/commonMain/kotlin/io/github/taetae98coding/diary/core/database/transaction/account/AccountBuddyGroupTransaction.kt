package io.github.taetae98coding.diary.core.database.transaction.account

import io.github.taetae98coding.diary.core.database.datasource.AccountBuddyGroupLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.AccountBuddyGroupLocalEntity
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class AccountBuddyGroupTransaction internal constructor(
    private val transactor: DatabaseTransactor,
    private val accountBuddyGroupLocalDataSource: AccountBuddyGroupLocalDataSource,
    private val buddyGroupLocalDataSource: BuddyGroupLocalDataSource,
) {
    public suspend fun upsert(accountId: Uuid, entity: List<BuddyGroupLocalEntity>) {
        transactor.transaction {
            buddyGroupLocalDataSource.upsert(entity)
            accountBuddyGroupLocalDataSource.upsert(entity.map { AccountBuddyGroupLocalEntity(accountId, it.id) })
        }
    }
}

package io.github.taetae98coding.diary.core.database.datasource

import androidx.paging.PagingSource
import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.AccountBuddyGroupLocalEntity
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class AccountBuddyGroupLocalDataSource internal constructor(
    private val database: DiaryDatabase,
    private val transactor: DatabaseTransactor,
) {

    public suspend fun delete(accountId: Uuid) {
        database.accountBuddyGroup().delete(accountId)
    }

    public suspend fun upsert(accountId: Uuid, entity: Collection<BuddyGroupLocalEntity>) {
        transactor.immediate {
            database.buddyGroup().upsert(entity)
            database.accountBuddyGroup().upsert(entity.map { AccountBuddyGroupLocalEntity(accountId, it.id) })
        }
    }

    public fun page(accountId: Uuid): PagingSource<Int, BuddyGroupLocalEntity> {
        return database.buddyGroup().page(accountId)
    }
}

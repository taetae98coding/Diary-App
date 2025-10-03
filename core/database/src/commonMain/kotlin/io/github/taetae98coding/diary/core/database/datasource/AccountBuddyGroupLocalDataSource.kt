package io.github.taetae98coding.diary.core.database.datasource

import androidx.paging.PagingSource
import io.github.taetae98coding.diary.core.database.DiaryDatabase
import io.github.taetae98coding.diary.core.database.entity.AccountBuddyGroupLocalEntity
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupLocalEntity
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class AccountBuddyGroupLocalDataSource internal constructor(
    private val database: DiaryDatabase,
) {

    public suspend fun delete(accountId: Uuid) {
        database.accountBuddyGroup().delete(accountId)
    }

    public suspend fun upsert(entity: Collection<AccountBuddyGroupLocalEntity>) {
        database.accountBuddyGroup().upsert(entity)
    }

    public fun page(accountId: Uuid): PagingSource<Int, BuddyGroupLocalEntity> {
        return database.buddyGroup().page(accountId)
    }
}

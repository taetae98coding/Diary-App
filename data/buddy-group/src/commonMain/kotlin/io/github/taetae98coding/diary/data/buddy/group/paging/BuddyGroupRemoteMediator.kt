package io.github.taetae98coding.diary.data.buddy.group.paging

import io.github.taetae98coding.diary.core.database.datasource.AccountBuddyGroupLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.buddy.BuddyGroupRemoteEntity
import io.github.taetae98coding.diary.library.paging.common.remotemediator.LimitOffsetRemoteMediator
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupRemoteMediator(
    private val account: Account.User,
    private val transactor: DatabaseTransactor,
    private val accountBuddyGroupLocalDataSource: AccountBuddyGroupLocalDataSource,
    private val buddyGroupRemoteDataSource: BuddyGroupRemoteDataSource,
) : LimitOffsetRemoteMediator<BuddyGroupLocalEntity>() {

    override suspend fun submit(limit: Int, offset: Int): List<BuddyGroupLocalEntity> {
        val response = buddyGroupRemoteDataSource.page(account.token, limit, offset)
            .requireSuccess()
            .requireBody()

        val local = response.data.map(BuddyGroupRemoteEntity::toLocal)

        transactor.immediate {
            accountBuddyGroupLocalDataSource.delete(account.id)
            accountBuddyGroupLocalDataSource.upsert(account.id, local)
        }

        return local
    }

    override suspend fun upsert(limit: Int, offset: Int): List<BuddyGroupLocalEntity> {
        val response = buddyGroupRemoteDataSource.page(account.token, limit, offset)
            .requireSuccess()
            .requireBody()
        val local = response.data.map(BuddyGroupRemoteEntity::toLocal)

        accountBuddyGroupLocalDataSource.upsert(account.id, local)

        return local
    }
}

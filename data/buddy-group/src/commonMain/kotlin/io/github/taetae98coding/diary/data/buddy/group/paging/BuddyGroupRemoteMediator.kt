package io.github.taetae98coding.diary.data.buddy.group.paging

import io.github.taetae98coding.diary.core.database.datasource.AccountBuddyGroupLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.database.transaction.account.AccountBuddyGroupTransaction
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.buddy.BuddyGroupRemoteEntity
import io.github.taetae98coding.diary.library.paging.common.remotemediator.LimitOffsetRemoteMediator

internal class BuddyGroupRemoteMediator(
    private val account: Account.User,
    private val transactor: DatabaseTransactor,
    private val accountBuddyGroupTransaction: AccountBuddyGroupTransaction,
    private val accountBuddyGroupLocalDataSource: AccountBuddyGroupLocalDataSource,
    private val buddyGroupRemoteDataSource: BuddyGroupRemoteDataSource,
) : LimitOffsetRemoteMediator<BuddyGroupLocalEntity>() {

    override suspend fun submit(limit: Int, offset: Int): List<BuddyGroupLocalEntity> {
        val response = buddyGroupRemoteDataSource.page(account.token, limit, offset)
            .requireSuccess()
            .requireBody()

        val data = response.data.map(BuddyGroupRemoteEntity::toLocal)

        transactor.transaction {
            accountBuddyGroupLocalDataSource.delete(account.id)
            accountBuddyGroupTransaction.upsert(account.id, response.data.map(BuddyGroupRemoteEntity::toLocal))
        }

        return data
    }

    override suspend fun upsert(limit: Int, offset: Int): List<BuddyGroupLocalEntity> {
        val response = buddyGroupRemoteDataSource.page(account.token, limit, offset)
            .requireSuccess()
            .requireBody()
        val data = response.data.map(BuddyGroupRemoteEntity::toLocal)

        accountBuddyGroupTransaction.upsert(account.id, response.data.map(BuddyGroupRemoteEntity::toLocal))
        return data
    }
}

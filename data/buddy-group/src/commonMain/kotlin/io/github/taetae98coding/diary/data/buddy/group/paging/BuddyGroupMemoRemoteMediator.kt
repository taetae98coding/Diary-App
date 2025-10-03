package io.github.taetae98coding.diary.data.buddy.group.paging

import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupMemoLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.database.transaction.buddy.BuddyGroupMemoTransaction
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupMemoRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.memo.MemoRemoteEntity
import io.github.taetae98coding.diary.library.paging.common.remotemediator.LimitOffsetRemoteMediator
import kotlin.uuid.Uuid

internal class BuddyGroupMemoRemoteMediator(
    private val account: Account.User,
    private val buddyGroupId: Uuid,
    private val transactor: DatabaseTransactor,
    private val buddyGroupMemoTransaction: BuddyGroupMemoTransaction,
    private val buddyGroupMemoLocalDataSource: BuddyGroupMemoLocalDataSource,
    private val buddyGroupMemoRemoteDataSource: BuddyGroupMemoRemoteDataSource,
) : LimitOffsetRemoteMediator<MemoLocalEntity>() {
    override suspend fun submit(limit: Int, offset: Int): List<MemoLocalEntity> {
        val response = buddyGroupMemoRemoteDataSource.page(account.token, buddyGroupId, limit, offset)
            .requireSuccess()
            .requireBody()

        val data = response.data.map(MemoRemoteEntity::toLocal)

        transactor.transaction {
            buddyGroupMemoLocalDataSource.delete(buddyGroupId)
            buddyGroupMemoTransaction.upsert(buddyGroupId, response.data.map(MemoRemoteEntity::toLocal))
        }

        return data
    }

    override suspend fun upsert(limit: Int, offset: Int): List<MemoLocalEntity> {
        val response = buddyGroupMemoRemoteDataSource.page(account.token, buddyGroupId, limit, offset)
            .requireSuccess()
            .requireBody()

        val data = response.data.map(MemoRemoteEntity::toLocal)

        buddyGroupMemoTransaction.upsert(buddyGroupId, response.data.map(MemoRemoteEntity::toLocal))
        return data
    }
}

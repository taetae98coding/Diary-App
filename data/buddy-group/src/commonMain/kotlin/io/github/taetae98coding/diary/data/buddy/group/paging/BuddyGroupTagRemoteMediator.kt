package io.github.taetae98coding.diary.data.buddy.group.paging

import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.TagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.database.transaction.buddy.BuddyGroupTagTransaction
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupTagRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.tag.TagRemoteEntity
import io.github.taetae98coding.diary.library.paging.common.remotemediator.LimitOffsetRemoteMediator
import kotlin.uuid.Uuid

internal class BuddyGroupTagRemoteMediator(
    private val account: Account.User,
    private val buddyGroupId: Uuid,
    private val transactor: DatabaseTransactor,
    private val buddyGroupTagTransaction: BuddyGroupTagTransaction,
    private val tagLocalDataSource: TagLocalDataSource,
    private val buddyGroupTagLocalDataSource: BuddyGroupTagLocalDataSource,
    private val buddyGroupTagRemoteDataSource: BuddyGroupTagRemoteDataSource,
) : LimitOffsetRemoteMediator<TagLocalEntity>() {
    override suspend fun submit(limit: Int, offset: Int): List<TagLocalEntity> {
        val response = buddyGroupTagRemoteDataSource.page(account.token, buddyGroupId, limit, offset)
            .requireSuccess()
            .requireBody()

        val data = response.data.map(TagRemoteEntity::toLocal)

        transactor.transaction {
            buddyGroupTagLocalDataSource.delete(buddyGroupId)
            buddyGroupTagTransaction.upsert(buddyGroupId, response.data.map(TagRemoteEntity::toLocal))
        }

        return data
    }

    override suspend fun upsert(limit: Int, offset: Int): List<TagLocalEntity> {
        val response = buddyGroupTagRemoteDataSource.page(account.token, buddyGroupId, limit, offset)
            .requireSuccess()
            .requireBody()

        val data = response.data.map(TagRemoteEntity::toLocal)

        buddyGroupTagTransaction.upsert(buddyGroupId, response.data.map(TagRemoteEntity::toLocal))

        return data
    }
}

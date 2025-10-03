package io.github.taetae98coding.diary.data.buddy.group.paging

import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.TagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoTagLocalEntity
import io.github.taetae98coding.diary.core.database.entity.SelectMemoTagLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupSelectMemoTagRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.tag.SelectMemoTagRemoteEntity
import io.github.taetae98coding.diary.library.paging.common.remotemediator.LimitOffsetRemoteMediator
import kotlin.time.Clock
import kotlin.uuid.Uuid

internal class BuddyGroupSelectMemoTagRemoteMediator(
    private val clock: Clock,
    private val transactor: DatabaseTransactor,
    private val account: Account.User,
    private val buddyGroupId: Uuid,
    private val memoId: Uuid,
    private val memoTagLocalDataSource: MemoTagLocalDataSource,
    private val tagLocalDataSource: TagLocalDataSource,
    private val buddyGroupTagLocalDataSource: BuddyGroupTagLocalDataSource,
    private val buddyGroupSelectMemoRemoteDataSource: BuddyGroupSelectMemoTagRemoteDataSource,
) : LimitOffsetRemoteMediator<SelectMemoTagLocalEntity>() {
    override suspend fun submit(limit: Int, offset: Int): List<SelectMemoTagLocalEntity> {
        val remote = buddyGroupSelectMemoRemoteDataSource.page(
            token = account.token,
            buddyGroupId = buddyGroupId,
            memoId = memoId,
            limit = limit,
            offset = offset,
        ).requireSuccess()
            .requireBody()

        val local = remote.data.map(SelectMemoTagRemoteEntity::toLocal)

        transactor.transaction {
            memoTagLocalDataSource.delete(memoId)
            buddyGroupTagLocalDataSource.delete(buddyGroupId)
            tagLocalDataSource.upsert(local.map { it.tag })
            buddyGroupTagLocalDataSource.upsert(local.map { BuddyGroupTagLocalEntity(buddyGroupId, it.tag.id) })
            memoTagLocalDataSource.upsert(local.map { MemoTagLocalEntity(memoId, it.tag.id, it.isSelected, clock.now().toEpochMilliseconds()) })
        }

        return local
    }

    override suspend fun upsert(limit: Int, offset: Int): List<SelectMemoTagLocalEntity> {
        val remote = buddyGroupSelectMemoRemoteDataSource.page(
            token = account.token,
            buddyGroupId = buddyGroupId,
            memoId = memoId,
            limit = limit,
            offset = offset,
        ).requireSuccess()
            .requireBody()

        val local = remote.data.map(SelectMemoTagRemoteEntity::toLocal)

        transactor.transaction {
            tagLocalDataSource.upsert(local.map { it.tag })
            buddyGroupTagLocalDataSource.upsert(local.map { BuddyGroupTagLocalEntity(buddyGroupId, it.tag.id) })
            memoTagLocalDataSource.upsert(local.map { MemoTagLocalEntity(memoId, it.tag.id, it.isSelected, 0L) })
        }

        return local
    }
}

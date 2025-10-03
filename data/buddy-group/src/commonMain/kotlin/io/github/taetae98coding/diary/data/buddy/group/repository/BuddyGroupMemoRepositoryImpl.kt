package io.github.taetae98coding.diary.data.buddy.group.repository

import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupMemoLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.mapper.toRemote
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupMemoRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.buddy.AddBuddyGroupMemoRequestEntity
import io.github.taetae98coding.diary.core.service.entity.memo.MemoDeleteRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.memo.MemoFinishRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.memo.MemoPrimaryRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.memotag.MemoTagRemoteEntity
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoRepository
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupMemoRepositoryImpl(
    private val transactor: DatabaseTransactor,
    private val memoLocalDataSource: MemoLocalDataSource,
    private val memoTagLocalDataSource: MemoTagLocalDataSource,
    private val buddyGroupMemoLocalDataSource: BuddyGroupMemoLocalDataSource,
    private val buddyGroupMemoRemoteDataSource: BuddyGroupMemoRemoteDataSource,
) : BuddyGroupMemoRepository {
    override suspend fun add(
        account: Account.User,
        buddyGroupId: Uuid,
        detail: MemoDetail,
        primaryTag: Uuid?,
        memoTagIds: Set<Uuid>,
    ) {
        val response = buddyGroupMemoRemoteDataSource.add(
            token = account.token,
            buddyGroupId = buddyGroupId,
            body = AddBuddyGroupMemoRequestEntity(
                detail = detail.toRemote(),
                primaryTag = primaryTag,
                memoTagIds = memoTagIds,
            ),
        )

        val remote = response.requireSuccess()
            .requireBody()

        transactor.immediate {
            buddyGroupMemoLocalDataSource.upsert(buddyGroupId, listOf(remote.memo.toLocal().copy(updatedAt = -1L)))
            memoTagLocalDataSource.upsert(remote.memoTags.map(MemoTagRemoteEntity::toLocal).map { it.copy(updatedAt = -1L) })
        }
    }

    override suspend fun update(
        account: Account.User,
        buddyGroupId: Uuid,
        memoId: Uuid,
        detail: MemoDetail,
    ) {
        val remote = buddyGroupMemoRemoteDataSource.update(account.token, buddyGroupId, memoId, detail.toRemote())
            .requireSuccess()
            .requireBody()

        memoLocalDataSource.upsert(listOf(remote.toLocal().copy(updatedAt = -1L)))
    }

    override suspend fun updateFinished(
        account: Account.User,
        buddyGroupId: Uuid,
        memoId: Uuid,
        isFinished: Boolean,
    ) {
        val remote = buddyGroupMemoRemoteDataSource.updateFinished(account.token, buddyGroupId, memoId, MemoFinishRemoteEntity(isFinished))
            .requireSuccess()
            .requireBody()

        memoLocalDataSource.upsert(listOf(remote.toLocal().copy(updatedAt = -1L)))
    }

    override suspend fun updateDeleted(
        account: Account.User,
        buddyGroupId: Uuid,
        memoId: Uuid,
        isDeleted: Boolean,
    ) {
        val remote = buddyGroupMemoRemoteDataSource.updateDeleted(account.token, buddyGroupId, memoId, MemoDeleteRemoteEntity(isDeleted))
            .requireSuccess()
            .requireBody()

        memoLocalDataSource.upsert(listOf(remote.toLocal().copy(updatedAt = -1L)))
    }

    override suspend fun updatePrimaryTag(account: Account.User, buddyGroupId: Uuid, memoId: Uuid, tagId: Uuid?) {
        val remote = buddyGroupMemoRemoteDataSource.updatePrimaryTag(account.token, buddyGroupId, memoId, MemoPrimaryRemoteEntity(tagId))
            .requireSuccess()
            .requireBody()

        memoLocalDataSource.upsert(listOf(remote.toLocal().copy(updatedAt = -1L)))
    }
}

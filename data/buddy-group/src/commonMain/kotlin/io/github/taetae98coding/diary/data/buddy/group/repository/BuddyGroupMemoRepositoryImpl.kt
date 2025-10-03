package io.github.taetae98coding.diary.data.buddy.group.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupMemoLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.database.transaction.buddy.BuddyGroupMemoTransaction
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.mapper.toRemote
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupMemoRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.buddy.AddBuddyGroupMemoRemoteEntity
import io.github.taetae98coding.diary.data.buddy.group.paging.BuddyGroupMemoRemoteMediator
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupMemoRepositoryImpl(
    private val transactor: DatabaseTransactor,
    private val buddyGroupMemoTransaction: BuddyGroupMemoTransaction,
    private val memoLocalDataSource: MemoLocalDataSource,
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
            entity = AddBuddyGroupMemoRemoteEntity(
                detail = detail.toRemote(),
                primaryTag = primaryTag,
                memoTagIds = memoTagIds,
            ),
        )

        val remote = response.requireSuccess()
            .requireBody()

        buddyGroupMemoTransaction.upsert(buddyGroupId, listOf(remote.toLocal()))
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

        memoLocalDataSource.upsert(listOf(remote.toLocal()))
    }

    override suspend fun updateFinished(
        account: Account.User,
        buddyGroupId: Uuid,
        memoId: Uuid,
        isFinished: Boolean,
    ) {
        try {
            memoLocalDataSource.updateFinished(memoId, isFinished)
            val remote = buddyGroupMemoRemoteDataSource.updateFinished(account.token, buddyGroupId, memoId, isFinished)
                .requireSuccess()
                .requireBody()

            memoLocalDataSource.upsert(listOf(remote.toLocal()))
        } catch (throwable: Throwable) {
            memoLocalDataSource.updateFinished(memoId, !isFinished)
            throw throwable
        }
    }

    override suspend fun updateDeleted(
        account: Account.User,
        buddyGroupId: Uuid,
        memoId: Uuid,
        isDeleted: Boolean,
    ) {
        try {
            memoLocalDataSource.updateDeleted(memoId, isDeleted)
            val remote = buddyGroupMemoRemoteDataSource.updateDeleted(account.token, buddyGroupId, memoId, isDeleted)
                .requireSuccess()
                .requireBody()
            memoLocalDataSource.upsert(listOf(remote.toLocal()))
        } catch (throwable: Throwable) {
            memoLocalDataSource.updateDeleted(memoId, !isDeleted)
            throw throwable
        }
    }

    override suspend fun updatePrimaryTag(account: Account.User, buddyGroupId: Uuid, memoId: Uuid, tagId: Uuid?) {
        val remote = buddyGroupMemoRemoteDataSource.updatePrimaryTag(account.token, buddyGroupId, memoId, tagId)
            .requireSuccess()
            .requireBody()

        memoLocalDataSource.upsert(listOf(remote.toLocal()))
    }

    override fun page(account: Account.User, buddyGroupId: Uuid): Flow<PagingData<Memo>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            remoteMediator = BuddyGroupMemoRemoteMediator(
                account = account,
                buddyGroupId = buddyGroupId,
                transactor = transactor,
                buddyGroupMemoTransaction = buddyGroupMemoTransaction,
                buddyGroupMemoLocalDataSource = buddyGroupMemoLocalDataSource,
                buddyGroupMemoRemoteDataSource = buddyGroupMemoRemoteDataSource,
            ),
            pagingSourceFactory = { buddyGroupMemoLocalDataSource.page(buddyGroupId) },
        )

        return pager.flow.mapPagingLatest(MemoLocalEntity::toEntity)
    }
}

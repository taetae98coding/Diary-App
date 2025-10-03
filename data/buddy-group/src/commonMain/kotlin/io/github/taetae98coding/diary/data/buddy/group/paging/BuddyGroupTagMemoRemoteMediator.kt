package io.github.taetae98coding.diary.data.buddy.group.paging

import io.github.taetae98coding.diary.core.database.datasource.MemoLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.TagMemoLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoTagLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupTagMemoRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.memo.MemoRemoteEntity
import io.github.taetae98coding.diary.library.paging.common.remotemediator.LimitOffsetRemoteMediator
import kotlin.time.Clock
import kotlin.uuid.Uuid

internal class BuddyGroupTagMemoRemoteMediator(
    private val clock: Clock,
    private val transactor: DatabaseTransactor,
    private val account: Account.User,
    private val buddyGroupId: Uuid,
    private val tagId: Uuid,
    private val memoLocalDataSource: MemoLocalDataSource,
    private val memoTagLocalDataSource: MemoTagLocalDataSource,
    private val tagMemoLocalDataSource: TagMemoLocalDataSource,
    private val buddyGroupTagMemoRemoteDataSource: BuddyGroupTagMemoRemoteDataSource,
) : LimitOffsetRemoteMediator<MemoLocalEntity>() {
    override suspend fun submit(limit: Int, offset: Int): List<MemoLocalEntity> {
        val remote = buddyGroupTagMemoRemoteDataSource.page(account.token, buddyGroupId, tagId, limit, offset)
            .requireSuccess()
            .requireBody()

        val local = remote.data.map(MemoRemoteEntity::toLocal)

        transactor.transaction {
            tagMemoLocalDataSource.delete(tagId)
            memoLocalDataSource.upsert(local)
            local.map {
                MemoTagLocalEntity(
                    memoId = it.id,
                    tagId = tagId,
                    isMemoTag = true,
                    updatedAt = clock.now().toEpochMilliseconds(),
                )
            }.also {
                memoTagLocalDataSource.upsert(it)
            }
        }

        return local
    }

    override suspend fun upsert(limit: Int, offset: Int): List<MemoLocalEntity> {
        val remote = buddyGroupTagMemoRemoteDataSource.page(account.token, buddyGroupId, tagId, limit, offset)
            .requireSuccess()
            .requireBody()

        val local = remote.data.map(MemoRemoteEntity::toLocal)

        transactor.transaction {
            memoLocalDataSource.upsert(local)
            local.map {
                MemoTagLocalEntity(
                    memoId = it.id,
                    tagId = tagId,
                    isMemoTag = true,
                    updatedAt = clock.now().toEpochMilliseconds(),
                )
            }.also {
                memoTagLocalDataSource.upsert(it)
            }
        }

        return local
    }
}

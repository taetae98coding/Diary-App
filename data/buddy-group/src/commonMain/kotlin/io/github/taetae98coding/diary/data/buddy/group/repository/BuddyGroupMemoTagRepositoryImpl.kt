package io.github.taetae98coding.diary.data.buddy.group.repository

import io.github.taetae98coding.diary.core.database.datasource.MemoLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.MemoTagLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupMemoTagRemoteDataSource
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoTagRepository
import kotlin.time.Clock
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupMemoTagRepositoryImpl(
    private val clock: Clock,
    private val transactor: DatabaseTransactor,
    private val buddyGroupMemoTagRemoteDataSource: BuddyGroupMemoTagRemoteDataSource,
    private val memoLocalDataSource: MemoLocalDataSource,
    private val memoTagLocalDataSource: MemoTagLocalDataSource,
) : BuddyGroupMemoTagRepository {
    override suspend fun updateMemoTag(account: Account.User, buddyGroupId: Uuid, memoId: Uuid, tagId: Uuid, isMemoTag: Boolean) {
        val memoRemote = buddyGroupMemoTagRemoteDataSource.updateMemoTag(
            token = account.token,
            buddyGroupId = buddyGroupId,
            memoId = memoId,
            tagId = tagId,
            isMemoTag = isMemoTag,
        ).requireSuccess()
            .requireBody()

        val memoTagLocal = MemoTagLocalEntity(
            memoId = memoId,
            tagId = tagId,
            isMemoTag = isMemoTag,
            updatedAt = clock.now().toEpochMilliseconds(),
        )

        transactor.transaction {
            memoLocalDataSource.upsert(listOf(memoRemote.toLocal()))
            memoTagLocalDataSource.upsert(listOf(memoTagLocal))
        }
    }
}

package io.github.taetae98coding.diary.data.buddy.group.repository

import io.github.taetae98coding.diary.core.database.datasource.MemoLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupMemoTagRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.memotag.UpdateMemoTagRequestEntity
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoTagRepository
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupMemoTagRepositoryImpl(
    private val transactor: DatabaseTransactor,
    private val buddyGroupMemoTagRemoteDataSource: BuddyGroupMemoTagRemoteDataSource,
    private val memoLocalDataSource: MemoLocalDataSource,
    private val memoTagLocalDataSource: MemoTagLocalDataSource,
) : BuddyGroupMemoTagRepository {
    override suspend fun updateMemoTag(account: Account.User, buddyGroupId: Uuid, memoId: Uuid, tagId: Uuid, isMemoTag: Boolean) {
        val response = buddyGroupMemoTagRemoteDataSource.updateMemoTag(
            token = account.token,
            buddyGroupId = buddyGroupId,
            body = UpdateMemoTagRequestEntity(
                memoId = memoId,
                tagId = tagId,
                isMemoTag = isMemoTag,
            ),
        )

        val remote = response.requireSuccess()
            .requireBody()

        transactor.immediate {
            memoLocalDataSource.upsert(listOf(remote.memo.toLocal().copy(updatedAt = -1L)))
            memoTagLocalDataSource.upsert(listOf(remote.memoTag.toLocal().copy(updatedAt = -1L)))
        }
    }
}

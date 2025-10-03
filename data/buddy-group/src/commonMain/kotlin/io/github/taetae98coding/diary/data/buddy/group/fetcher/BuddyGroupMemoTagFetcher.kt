package io.github.taetae98coding.diary.data.buddy.group.fetcher

import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupMemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.MemoTagLocalEntity
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupMemoTagRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.memotag.MemoTagRemoteEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupMemoTagFetcher(
    private val memoTagLocalDataSource: MemoTagLocalDataSource,
    private val buddyGroupMemoTagLocalDataSource: BuddyGroupMemoTagLocalDataSource,
    private val buddyGroupMemoTagRemoteDataSource: BuddyGroupMemoTagRemoteDataSource,
) : BuddyGroupFetcher<MemoTagLocalEntity, MemoTagRemoteEntity> {
    override fun mapper(remote: MemoTagRemoteEntity): MemoTagLocalEntity {
        return remote.toLocal()
    }

    override fun getLastUpdateAt(buddyGroupId: Uuid): Flow<Long?> {
        return buddyGroupMemoTagLocalDataSource.getLastUpdateAt(buddyGroupId)
    }

    override suspend fun pull(account: Account.User, buddyGroupId: Uuid, updatedAt: Long): List<MemoTagRemoteEntity> {
        val response = buddyGroupMemoTagRemoteDataSource.fetch(
            token = account.token,
            buddyGroupId = buddyGroupId,
            updatedAt = updatedAt,
        )

        return response.requireSuccess()
            .requireBody()
    }

    override suspend fun push(account: Account.User, buddyGroupId: Uuid, data: List<MemoTagLocalEntity>) {
        memoTagLocalDataSource.upsert(data)
    }
}

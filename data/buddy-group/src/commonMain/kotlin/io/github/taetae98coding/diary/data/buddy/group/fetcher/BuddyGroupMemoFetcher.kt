package io.github.taetae98coding.diary.data.buddy.group.fetcher

import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupMemoLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupMemoRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.memo.MemoRemoteEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupMemoFetcher(
    private val buddyGroupMemoLocalDataSource: BuddyGroupMemoLocalDataSource,
    private val buddyGroupMemoRemoteDataSource: BuddyGroupMemoRemoteDataSource,
) : BuddyGroupFetcher<MemoLocalEntity, MemoRemoteEntity> {
    override fun mapper(remote: MemoRemoteEntity): MemoLocalEntity {
        return remote.toLocal()
    }

    override fun getLastUpdateAt(buddyGroupId: Uuid): Flow<Long?> {
        return buddyGroupMemoLocalDataSource.getLastUpdateAt(buddyGroupId)
    }

    override suspend fun pull(account: Account.User, buddyGroupId: Uuid, updatedAt: Long): List<MemoRemoteEntity> {
        val response = buddyGroupMemoRemoteDataSource.fetch(
            token = account.token,
            buddyGroupId = buddyGroupId,
            updatedAt = updatedAt,
        )

        return response.requireSuccess()
            .requireBody()
    }

    override suspend fun push(account: Account.User, buddyGroupId: Uuid, data: List<MemoLocalEntity>) {
        buddyGroupMemoLocalDataSource.upsert(buddyGroupId, data)
    }
}

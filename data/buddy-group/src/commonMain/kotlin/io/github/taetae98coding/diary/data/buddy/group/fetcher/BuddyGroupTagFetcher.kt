package io.github.taetae98coding.diary.data.buddy.group.fetcher

import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupTagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupTagRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.tag.TagRemoteEntity
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupTagFetcher(
    private val buddyGroupTagLocalDataSource: BuddyGroupTagLocalDataSource,
    private val buddyGroupTagRemoteDataSource: BuddyGroupTagRemoteDataSource,
) : BuddyGroupFetcher<TagLocalEntity, TagRemoteEntity> {
    override fun mapper(remote: TagRemoteEntity): TagLocalEntity {
        return remote.toLocal()
    }

    override fun getLastUpdateAt(buddyGroupId: Uuid): Flow<Long?> {
        return buddyGroupTagLocalDataSource.getLastUpdateAt(buddyGroupId)
    }

    override suspend fun pull(account: Account.User, buddyGroupId: Uuid, updatedAt: Long): List<TagRemoteEntity> {
        val response = buddyGroupTagRemoteDataSource.fetch(
            token = account.token,
            buddyGroupId = buddyGroupId,
            updatedAt = updatedAt,
        )

        return response.requireSuccess()
            .requireBody()
    }

    override suspend fun push(account: Account.User, buddyGroupId: Uuid, data: List<TagLocalEntity>) {
        buddyGroupTagLocalDataSource.upsert(buddyGroupId, data)
    }
}

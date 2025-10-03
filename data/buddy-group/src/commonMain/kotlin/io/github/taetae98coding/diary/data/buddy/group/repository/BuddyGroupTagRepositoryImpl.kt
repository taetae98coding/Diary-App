package io.github.taetae98coding.diary.data.buddy.group.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.TagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.mapper.toRemote
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupTagRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.buddy.AddBuddyGroupTagRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.tag.TagDeleteRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.tag.TagFinishRemoteEntity
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupTagRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupTagRepositoryImpl(
    private val tagLocalDataSource: TagLocalDataSource,
    private val buddyGroupTagLocalDataSource: BuddyGroupTagLocalDataSource,
    private val buddyGroupTagRemoteDataSource: BuddyGroupTagRemoteDataSource,
) : BuddyGroupTagRepository {
    override suspend fun add(
        account: Account.User,
        buddyGroupId: Uuid,
        detail: TagDetail,
    ) {
        val response = buddyGroupTagRemoteDataSource.add(
            token = account.token,
            buddyGroupId = buddyGroupId,
            body = AddBuddyGroupTagRemoteEntity(
                detail = detail.toRemote(),
            ),
        )

        val remote = response.requireSuccess()
            .requireBody()

        buddyGroupTagLocalDataSource.upsert(buddyGroupId, listOf(remote.toLocal().copy(updatedAt = -1L)))
    }

    override suspend fun update(account: Account.User, buddyGroupId: Uuid, tagId: Uuid, detail: TagDetail) {
        val remote = buddyGroupTagRemoteDataSource.update(account.token, buddyGroupId, tagId, detail.toRemote())
            .requireSuccess()
            .requireBody()

        tagLocalDataSource.upsert(listOf(remote.toLocal().copy(updatedAt = -1L)))
    }

    override suspend fun updateFinished(account: Account.User, buddyGroupId: Uuid, tagId: Uuid, isFinished: Boolean) {
        val remote = buddyGroupTagRemoteDataSource.updateFinished(account.token, buddyGroupId, tagId, TagFinishRemoteEntity(isFinished))
            .requireSuccess()
            .requireBody()

        tagLocalDataSource.upsert(listOf(remote.toLocal().copy(updatedAt = -1L)))
    }

    override suspend fun updateDeleted(account: Account.User, buddyGroupId: Uuid, tagId: Uuid, isDeleted: Boolean) {
        val remote = buddyGroupTagRemoteDataSource.updateDeleted(account.token, buddyGroupId, tagId, TagDeleteRemoteEntity(isDeleted))
            .requireSuccess()
            .requireBody()

        tagLocalDataSource.upsert(listOf(remote.toLocal().copy(updatedAt = -1L)))
    }

    override fun page(account: Account.User, buddyGroupId: Uuid): Flow<PagingData<Tag>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { buddyGroupTagLocalDataSource.page(buddyGroupId) },
        )

        return pager.flow.mapPagingLatest(TagLocalEntity::toEntity)
    }
}

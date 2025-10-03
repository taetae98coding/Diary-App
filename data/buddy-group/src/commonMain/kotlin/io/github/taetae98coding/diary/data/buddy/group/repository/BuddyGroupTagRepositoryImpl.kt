package io.github.taetae98coding.diary.data.buddy.group.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.TagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.database.transaction.buddy.BuddyGroupTagTransaction
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.mapper.toRemote
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupTagRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.buddy.AddBuddyGroupTagRemoteEntity
import io.github.taetae98coding.diary.data.buddy.group.paging.BuddyGroupTagRemoteMediator
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupTagRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupTagRepositoryImpl(
    private val transactor: DatabaseTransactor,
    private val buddyGroupTagTransaction: BuddyGroupTagTransaction,
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
            entity = AddBuddyGroupTagRemoteEntity(
                detail = detail.toRemote(),
            ),
        )

        val remote = response.requireSuccess()
            .requireBody()

        buddyGroupTagTransaction.upsert(buddyGroupId, listOf(remote.toLocal()))
    }

    override suspend fun update(account: Account.User, buddyGroupId: Uuid, tagId: Uuid, detail: TagDetail) {
        val remote = buddyGroupTagRemoteDataSource.update(account.token, buddyGroupId, tagId, detail.toRemote())
            .requireSuccess()
            .requireBody()

        tagLocalDataSource.upsert(listOf(remote.toLocal()))
    }

    override suspend fun updateFinished(account: Account.User, buddyGroupId: Uuid, tagId: Uuid, isFinished: Boolean) {
        try {
            tagLocalDataSource.updateFinished(tagId, isFinished)
            val remote = buddyGroupTagRemoteDataSource.updateFinished(account.token, buddyGroupId, tagId, isFinished)
                .requireSuccess()
                .requireBody()

            tagLocalDataSource.upsert(listOf(remote.toLocal()))
        } catch (throwable: Throwable) {
            tagLocalDataSource.updateFinished(tagId, !isFinished)
            throw throwable
        }
    }

    override suspend fun updateDeleted(account: Account.User, buddyGroupId: Uuid, tagId: Uuid, isDeleted: Boolean) {
        try {
            tagLocalDataSource.updateDeleted(tagId, isDeleted)
            val remote = buddyGroupTagRemoteDataSource.updateDeleted(account.token, buddyGroupId, tagId, isDeleted)
                .requireSuccess()
                .requireBody()

            tagLocalDataSource.upsert(listOf(remote.toLocal()))
        } catch (throwable: Throwable) {
            tagLocalDataSource.updateDeleted(tagId, !isDeleted)
            throw throwable
        }
    }

    override fun page(account: Account.User, buddyGroupId: Uuid): Flow<PagingData<Tag>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            remoteMediator = BuddyGroupTagRemoteMediator(
                account = account,
                buddyGroupId = buddyGroupId,
                transactor = transactor,
                buddyGroupTagTransaction = buddyGroupTagTransaction,
                tagLocalDataSource = tagLocalDataSource,
                buddyGroupTagLocalDataSource = buddyGroupTagLocalDataSource,
                buddyGroupTagRemoteDataSource = buddyGroupTagRemoteDataSource,
            ),
            pagingSourceFactory = { buddyGroupTagLocalDataSource.page(buddyGroupId) },
        )

        return pager.flow.mapPagingLatest(TagLocalEntity::toEntity)
    }
}

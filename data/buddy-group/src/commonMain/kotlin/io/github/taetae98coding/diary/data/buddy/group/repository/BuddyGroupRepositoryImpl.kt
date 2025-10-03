package io.github.taetae98coding.diary.data.buddy.group.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import io.github.taetae98coding.diary.core.database.datasource.AccountBuddyGroupLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroup
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroupDetail
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.mapper.toRemote
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.buddy.BuddyAddRemoteEntity
import io.github.taetae98coding.diary.data.buddy.group.paging.BuddyGroupRemoteMediator
import io.github.taetae98coding.diary.domain.buddy.group.exception.BuddyGroupNotFoundException
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

@Factory
internal class BuddyGroupRepositoryImpl(
    private val buddyGroupLocalDataSource: BuddyGroupLocalDataSource,
    private val accountBuddyGroupLocalDataSource: AccountBuddyGroupLocalDataSource,
    private val buddyGroupRemoteDataSource: BuddyGroupRemoteDataSource,
) : BuddyGroupRepository,
    KoinComponent {
    override suspend fun fetch(account: Account.User, buddyGroupId: Uuid) {
        val response = buddyGroupRemoteDataSource.get(account.token, buddyGroupId)

        if (response.code == DiaryRemoteEntity.NOT_FOUND) throw BuddyGroupNotFoundException()

        response.requireSuccess()
            .requireBody()
            .also { buddyGroupLocalDataSource.upsert(listOf(it.toLocal())) }
    }

    override suspend fun add(account: Account.User, detail: BuddyGroupDetail, buddyIds: Collection<Uuid>) {
        val buddyAddRemoteEntity = BuddyAddRemoteEntity(
            detail = detail.toRemote(),
            buddyIds = buddyIds.toSet(),
        )

        val remote = buddyGroupRemoteDataSource.add(account.token, buddyAddRemoteEntity)
            .requireSuccess()
            .requireBody()

        accountBuddyGroupLocalDataSource.upsert(account.id, listOf(remote.toLocal()))
    }

    override suspend fun update(account: Account.User, buddyGroupId: Uuid, detail: BuddyGroupDetail) {
        val response = buddyGroupRemoteDataSource.update(account.token, buddyGroupId, detail.toRemote())

        if (response.code == DiaryRemoteEntity.NOT_FOUND) throw BuddyGroupNotFoundException()

        response.requireSuccess()
            .requireBody()
            .also { buddyGroupLocalDataSource.upsert(listOf(it.toLocal())) }
    }

    override fun get(buddyGroupId: Uuid): Flow<BuddyGroup?> {
        return buddyGroupLocalDataSource.get(buddyGroupId)
            .mapLatest { it?.toEntity() }
    }

    override fun page(account: Account.User): Flow<PagingData<BuddyGroup>> {
        val remoteMediator by inject<BuddyGroupRemoteMediator> {
            parametersOf(account)
        }
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { accountBuddyGroupLocalDataSource.page(account.id) },
        )

        return pager.flow.mapLatest { it.map(BuddyGroupLocalEntity::toEntity) }
    }
}

package io.github.taetae98coding.diary.data.buddy.group.paging

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import io.github.taetae98coding.diary.core.database.datasource.AccountBuddyGroupLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.BuddyGroupLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.buddy.BuddyGroupRemoteEntity
import io.github.taetae98coding.diary.library.paging.common.clippedRefreshKey
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupRemoteMediator(
    private val account: Account.User,
    private val transactor: DatabaseTransactor,
    private val accountBuddyGroupLocalDataSource: AccountBuddyGroupLocalDataSource,
    private val buddyGroupRemoteDataSource: BuddyGroupRemoteDataSource,
) : RemoteMediator<Int, BuddyGroupLocalEntity>() {
    private var prependKey = 0
    private var appendKey = 0

    private suspend fun submit(limit: Int, offset: Int): List<BuddyGroupLocalEntity> {
        val response = buddyGroupRemoteDataSource.page(account.token, limit, offset)
            .requireSuccess()
            .requireBody()

        val local = response.data.map(BuddyGroupRemoteEntity::toLocal)

        transactor.immediate {
            accountBuddyGroupLocalDataSource.delete(account.id)
            accountBuddyGroupLocalDataSource.upsert(account.id, local)
        }

        return local
    }

    private suspend fun upsert(limit: Int, offset: Int): List<BuddyGroupLocalEntity> {
        val response = buddyGroupRemoteDataSource.page(account.token, limit, offset)
            .requireSuccess()
            .requireBody()
        val local = response.data.map(BuddyGroupRemoteEntity::toLocal)

        accountBuddyGroupLocalDataSource.upsert(account.id, local)

        return local
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, BuddyGroupLocalEntity>): MediatorResult {
        return try {
            when(loadType) {
                LoadType.REFRESH -> refresh(state)
                LoadType.PREPEND -> prepend(state)
                LoadType.APPEND -> append(state)
            }
        } catch (throwable: Throwable) {
            MediatorResult.Error(throwable)
        }
    }

    private suspend fun refresh(state: PagingState<Int, BuddyGroupLocalEntity>): MediatorResult.Success {
        val limit = state.config.initialLoadSize
        val offset = state.clippedRefreshKey() ?: 0
        val response = submit(limit, offset)

        prependKey = offset
        appendKey = offset + response.size

        return MediatorResult.Success(response.size < limit)
    }

    private suspend fun prepend(state: PagingState<Int, BuddyGroupLocalEntity>): MediatorResult.Success {
        val limit = minOf(prependKey, state.config.pageSize)
        val offset = maxOf(0, prependKey - state.config.pageSize)

        if (limit == 0) {
            return MediatorResult.Success(true)
        }

        upsert(limit, offset)
        prependKey = offset

        return MediatorResult.Success(offset <= 0)
    }

    private suspend fun append(state: PagingState<Int, BuddyGroupLocalEntity>): MediatorResult.Success {
        val limit = state.config.pageSize
        val offset = appendKey
        val response = upsert(limit, offset)

        appendKey = offset + response.size

        return MediatorResult.Success(response.size < limit)
    }
}

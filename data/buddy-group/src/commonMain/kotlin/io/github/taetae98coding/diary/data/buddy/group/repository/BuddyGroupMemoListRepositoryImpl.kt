package io.github.taetae98coding.diary.data.buddy.group.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupMemoListLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoListRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupMemoListRepositoryImpl(
    private val buddyGroupMemoListLocalDataSource: BuddyGroupMemoListLocalDataSource,
) : BuddyGroupMemoListRepository {
    override fun page(account: Account.User, buddyGroupId: Uuid): Flow<PagingData<Memo>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { buddyGroupMemoListLocalDataSource.page(buddyGroupId) },
        )

        return pager.flow.mapPagingLatest(MemoLocalEntity::toEntity)
    }
}

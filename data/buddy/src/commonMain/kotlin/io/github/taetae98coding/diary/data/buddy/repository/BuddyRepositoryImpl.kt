package io.github.taetae98coding.diary.data.buddy.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.buddy.Buddy
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.core.service.datasource.BuddyRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.buddy.BuddyRemoteEntity
import io.github.taetae98coding.diary.data.buddy.paging.SearchBuddyPagingSource
import io.github.taetae98coding.diary.domain.buddy.repository.BuddyRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyRepositoryImpl(
    private val buddyRemoteDataSource: BuddyRemoteDataSource,
) : BuddyRepository {
    override fun search(account: Account, query: String): Flow<PagingData<Buddy>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = {
                SearchBuddyPagingSource(
                    account = account,
                    query = query,
                    buddyRemoteDataSource = buddyRemoteDataSource,
                )
            },
        )

        return pager.flow.mapPagingLatest(BuddyRemoteEntity::toEntity)
    }
}

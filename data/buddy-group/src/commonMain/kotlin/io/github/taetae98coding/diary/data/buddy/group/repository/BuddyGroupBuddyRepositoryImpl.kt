package io.github.taetae98coding.diary.data.buddy.group.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.buddy.Buddy
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupBuddyRemoteDataSource
import io.github.taetae98coding.diary.data.buddy.group.paging.BuddyGroupBuddyPagingSource
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupBuddyRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupBuddyRepositoryImpl(
    private val buddyGroupBuddyRemoteDataSource: BuddyGroupBuddyRemoteDataSource,
) : BuddyGroupBuddyRepository {
    override fun page(account: Account.User, buddyGroupId: Uuid): Flow<PagingData<Buddy>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = {
                BuddyGroupBuddyPagingSource(
                    account = account,
                    buddyGroupId = buddyGroupId,
                    buddyGroupBuddyRemoteDataSource = buddyGroupBuddyRemoteDataSource,
                )
            },
        )

        return pager.flow
    }
}

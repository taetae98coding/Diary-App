package io.github.taetae98coding.diary.data.buddy.group.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupFinishedTagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupFinishedTagRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupFinishedTagRepositoryImpl(
    private val localDataSource: BuddyGroupFinishedTagLocalDataSource,
) : BuddyGroupFinishedTagRepository {
    override fun page(account: Account.User, buddyGroupId: Uuid): Flow<PagingData<Tag>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { localDataSource.page(buddyGroupId) },
        )

        return pager.flow.mapPagingLatest(TagLocalEntity::toEntity)
    }
}

package io.github.taetae98coding.diary.data.buddy.group.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupSelectMemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.SelectMemoTagLocalEntity
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.SelectMemoTag
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupSelectMemoTagRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupSelectMemoTagRepositoryImpl(
    private val buddyGroupSelectMemoTagLocalDataSource: BuddyGroupSelectMemoTagLocalDataSource,
) : BuddyGroupSelectMemoTagRepository {
    override fun page(
        account: Account.User,
        buddyGroupId: Uuid,
        memoId: Uuid,
    ): Flow<PagingData<SelectMemoTag>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { buddyGroupSelectMemoTagLocalDataSource.page(buddyGroupId, memoId) },
        )

        return pager.flow.mapPagingLatest(SelectMemoTagLocalEntity::toEntity)
    }
}

package io.github.taetae98coding.diary.data.buddy.group.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupSelectMemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.TagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.SelectMemoTagLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.SelectMemoTag
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupSelectMemoTagRemoteDataSource
import io.github.taetae98coding.diary.data.buddy.group.paging.BuddyGroupSelectMemoTagRemoteMediator
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupSelectMemoTagRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlin.time.Clock
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupSelectMemoTagRepositoryImpl(
    private val clock: Clock,
    private val transactor: DatabaseTransactor,
    private val memoTagLocalDataSource: MemoTagLocalDataSource,
    private val tagLocalDataSource: TagLocalDataSource,
    private val buddyGroupTagLocalDataSource: BuddyGroupTagLocalDataSource,
    private val buddyGroupSelectMemoTagLocalDataSource: BuddyGroupSelectMemoTagLocalDataSource,
    private val buddyGroupSelectMemoRemoteDataSource: BuddyGroupSelectMemoTagRemoteDataSource,
) : BuddyGroupSelectMemoTagRepository {
    override fun page(
        account: Account.User,
        buddyGroupId: Uuid,
        memoId: Uuid,
    ): Flow<PagingData<SelectMemoTag>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            remoteMediator = BuddyGroupSelectMemoTagRemoteMediator(
                clock = clock,
                transactor = transactor,
                account = account,
                buddyGroupId = buddyGroupId,
                memoId = memoId,
                memoTagLocalDataSource = memoTagLocalDataSource,
                tagLocalDataSource = tagLocalDataSource,
                buddyGroupTagLocalDataSource = buddyGroupTagLocalDataSource,
                buddyGroupSelectMemoRemoteDataSource = buddyGroupSelectMemoRemoteDataSource,
            ),
            pagingSourceFactory = { buddyGroupSelectMemoTagLocalDataSource.page(buddyGroupId, memoId) },
        )

        return pager.flow.mapPagingLatest(SelectMemoTagLocalEntity::toEntity)
    }
}

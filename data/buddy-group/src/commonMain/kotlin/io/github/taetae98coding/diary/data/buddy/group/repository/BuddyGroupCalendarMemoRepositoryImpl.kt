package io.github.taetae98coding.diary.data.buddy.group.repository

import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupCalendarMemoLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupMemoLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.CalendarMemoLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoTagLocalEntity
import io.github.taetae98coding.diary.core.database.transaction.DatabaseTransactor
import io.github.taetae98coding.diary.core.database.transaction.buddy.BuddyGroupMemoTransaction
import io.github.taetae98coding.diary.core.database.transaction.buddy.BuddyGroupTagTransaction
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.CalendarMemo
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.core.service.datasource.BuddyGroupCalendarMemoRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.memo.MemoRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.tag.TagRemoteEntity
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupCalendarMemoRepository
import io.github.taetae98coding.diary.library.kotlinx.coroutines.core.flow.mapCollectionLatest
import kotlin.time.Clock
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateRange
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupCalendarMemoRepositoryImpl(
    private val clock: Clock,
    private val transactor: DatabaseTransactor,
    private val memoTagLocalDataSource: MemoTagLocalDataSource,
    private val buddyGroupMemoTransaction: BuddyGroupMemoTransaction,
    private val buddyGroupTagTransaction: BuddyGroupTagTransaction,
    private val buddyGroupMemoLocalDataSource: BuddyGroupMemoLocalDataSource,
    private val calendarMemoLocalDataSource: BuddyGroupCalendarMemoLocalDataSource,
    private val buddyGroupCalendarMemoRemoteDataSource: BuddyGroupCalendarMemoRemoteDataSource,
) : BuddyGroupCalendarMemoRepository {

    override fun get(buddyGroupId: Uuid, dateRange: LocalDateRange): Flow<List<CalendarMemo>> {
        return calendarMemoLocalDataSource.get(buddyGroupId, dateRange)
            .mapCollectionLatest(CalendarMemoLocalEntity::toEntity)
    }

    override suspend fun fetch(account: Account.User, buddyGroupId: Uuid, dateRange: LocalDateRange) {
        val remote = buddyGroupCalendarMemoRemoteDataSource.get(account.token, buddyGroupId, dateRange)
            .requireSuccess()
            .requireBody()

        transactor.transaction {
            buddyGroupMemoLocalDataSource.deleteBydDateRange(buddyGroupId, dateRange)
            buddyGroupTagTransaction.upsert(buddyGroupId, remote.tag.map(TagRemoteEntity::toLocal))
            buddyGroupMemoTransaction.upsert(buddyGroupId, remote.memo.map(MemoRemoteEntity::toLocal))

            remote.memo.mapNotNull { memo ->
                val primaryTag = memo.primaryTag
                if (primaryTag == null) {
                    null
                } else {
                    MemoTagLocalEntity(
                        memoId = memo.id,
                        tagId = primaryTag,
                        isMemoTag = true,
                        updatedAt = clock.now().toEpochMilliseconds(),
                    )
                }
            }.also {
                memoTagLocalDataSource.upsert(it)
            }
        }
    }
}

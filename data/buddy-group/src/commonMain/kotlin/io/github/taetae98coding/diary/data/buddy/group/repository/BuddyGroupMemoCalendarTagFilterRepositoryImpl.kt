package io.github.taetae98coding.diary.data.buddy.group.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupMemoCalendarTagFilterLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoCalendarTagFilterLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.MemoCalendarTagFilterLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagFilterLocalEntity
import io.github.taetae98coding.diary.core.entity.tag.TagFilter
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoCalendarTagFilterRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupMemoCalendarTagFilterRepositoryImpl(
    private val memoCalendarTagFilterLocalDataSource: MemoCalendarTagFilterLocalDataSource,
    private val buddyGroupMemoCalendarTagFilterLocalDataSource: BuddyGroupMemoCalendarTagFilterLocalDataSource,
) : BuddyGroupMemoCalendarTagFilterRepository {
    override suspend fun upsert(buddyGroupId: Uuid, tagId: Uuid, isFilter: Boolean) {
        memoCalendarTagFilterLocalDataSource.upsert(listOf(MemoCalendarTagFilterLocalEntity(tagId, isFilter)))
    }

    override fun hasFilter(buddyGroupId: Uuid): Flow<Boolean> {
        return buddyGroupMemoCalendarTagFilterLocalDataSource.hasFilter(buddyGroupId)
    }

    override fun page(buddyGroupId: Uuid): Flow<PagingData<TagFilter>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { buddyGroupMemoCalendarTagFilterLocalDataSource.page(buddyGroupId) },
        )

        return pager.flow.mapPagingLatest(TagFilterLocalEntity::toEntity)
    }
}

package io.github.taetae98coding.diary.data.buddy.group.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.BuddyGroupMemoListTagFilterLocalDataSource
import io.github.taetae98coding.diary.core.database.datasource.MemoListTagFilterLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.MemoListTagFilterLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagFilterLocalEntity
import io.github.taetae98coding.diary.core.entity.tag.TagFilter
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoListTagFilterRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupMemoListTagFilterRepositoryImpl(
    private val memoListTagFilterLocalDataSource: MemoListTagFilterLocalDataSource,
    private val buddyGroupMemoListTagFilterLocalDataSource: BuddyGroupMemoListTagFilterLocalDataSource,
) : BuddyGroupMemoListTagFilterRepository {
    override suspend fun upsert(buddyGroupId: Uuid, tagId: Uuid, isFilter: Boolean) {
        memoListTagFilterLocalDataSource.upsert(listOf(MemoListTagFilterLocalEntity(tagId, isFilter)))
    }

    override fun hasFilter(buddyGroupId: Uuid): Flow<Boolean> {
        return buddyGroupMemoListTagFilterLocalDataSource.hasFilter(buddyGroupId)
    }

    override fun page(buddyGroupId: Uuid): Flow<PagingData<TagFilter>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { buddyGroupMemoListTagFilterLocalDataSource.page(buddyGroupId) },
        )

        return pager.flow.mapPagingLatest(TagFilterLocalEntity::toEntity)
    }
}

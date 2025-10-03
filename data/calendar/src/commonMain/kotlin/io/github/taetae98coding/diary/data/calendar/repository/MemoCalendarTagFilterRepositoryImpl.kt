package io.github.taetae98coding.diary.data.calendar.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.MemoCalendarTagFilterLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.MemoCalendarTagFilterLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagFilterLocalEntity
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.TagFilter
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.calendar.repository.MemoCalendarTagFilterRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class MemoCalendarTagFilterRepositoryImpl(
    private val memoCalendarTagFilterLocalDataSource: MemoCalendarTagFilterLocalDataSource,
) : MemoCalendarTagFilterRepository {
    override suspend fun upsert(tagId: Uuid, isFilter: Boolean) {
        memoCalendarTagFilterLocalDataSource.upsert(listOf(MemoCalendarTagFilterLocalEntity(tagId, isFilter)))
    }

    override fun hasFilter(account: Account): Flow<Boolean> {
        return memoCalendarTagFilterLocalDataSource.hasFilter(account.id)
    }

    override fun page(account: Account): Flow<PagingData<TagFilter>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { memoCalendarTagFilterLocalDataSource.page(account.id) },
        )

        return pager.flow.mapPagingLatest(TagFilterLocalEntity::toEntity)
    }
}

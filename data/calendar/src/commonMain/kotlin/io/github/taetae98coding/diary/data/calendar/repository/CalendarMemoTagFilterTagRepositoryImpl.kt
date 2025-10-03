package io.github.taetae98coding.diary.data.calendar.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.CalendarMemoTagFilterLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.CalendarMemoTagFilterLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoTagFilterLocalEntity
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.MemoTagFilter
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.calendar.repository.CalendarMemoTagFilterTagRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class CalendarMemoTagFilterTagRepositoryImpl(
    private val calendarMemoTagFilterLocalDataSource: CalendarMemoTagFilterLocalDataSource,
) : CalendarMemoTagFilterTagRepository {
    override suspend fun upsert(tagId: Uuid, isFilter: Boolean) {
        calendarMemoTagFilterLocalDataSource.upsert(listOf(CalendarMemoTagFilterLocalEntity(tagId, isFilter)))
    }

    override fun hasFilter(account: Account): Flow<Boolean> {
        return calendarMemoTagFilterLocalDataSource.hasFilter(account.id)
    }

    override fun page(account: Account): Flow<PagingData<MemoTagFilter>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { calendarMemoTagFilterLocalDataSource.page(account.id) },
        )

        return pager.flow.mapPagingLatest(MemoTagFilterLocalEntity::toEntity)
    }
}

package io.github.taetae98coding.diary.data.memo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.MemoListTagFilterLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.MemoListTagFilterLocalEntity
import io.github.taetae98coding.diary.core.database.entity.TagFilterLocalEntity
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.TagFilter
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.memo.repository.MemoListTagFilterRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class MemoListTagFilterRepositoryImpl(
    private val memoListTagFilterLocalDataSource: MemoListTagFilterLocalDataSource,
) : MemoListTagFilterRepository {

    override suspend fun upsert(tagId: Uuid, isFilter: Boolean) {
        memoListTagFilterLocalDataSource.upsert(listOf(MemoListTagFilterLocalEntity(tagId, isFilter)))
    }

    override fun hasFilter(account: Account): Flow<Boolean> {
        return memoListTagFilterLocalDataSource.hasFilter(account.id)
    }

    override fun page(account: Account): Flow<PagingData<TagFilter>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { memoListTagFilterLocalDataSource.page(account.id) },
        )

        return pager.flow.mapPagingLatest(TagFilterLocalEntity::toEntity)
    }
}

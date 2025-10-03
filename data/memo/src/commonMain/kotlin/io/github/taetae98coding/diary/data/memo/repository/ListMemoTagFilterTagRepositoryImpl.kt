package io.github.taetae98coding.diary.data.memo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.ListMemoTagFilterLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.ListMemoTagFilterLocalEntity
import io.github.taetae98coding.diary.core.database.entity.MemoTagFilterLocalEntity
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.MemoTagFilter
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.memo.repository.ListMemoTagFilterTagRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class ListMemoTagFilterTagRepositoryImpl(
    private val listMemoTagFilterLocalDataSource: ListMemoTagFilterLocalDataSource,
) : ListMemoTagFilterTagRepository {

    override suspend fun upsert(tagId: Uuid, isFilter: Boolean) {
        listMemoTagFilterLocalDataSource.upsert(listOf(ListMemoTagFilterLocalEntity(tagId, isFilter)))
    }

    override fun hasFilter(account: Account): Flow<Boolean> {
        return listMemoTagFilterLocalDataSource.hasFilter(account.id)
    }

    override fun page(account: Account): Flow<PagingData<MemoTagFilter>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { listMemoTagFilterLocalDataSource.page(account.id) },
        )

        return pager.flow.mapPagingLatest(MemoTagFilterLocalEntity::toEntity)
    }
}

package io.github.taetae98coding.diary.data.memo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.MemoSearchLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.memo.repository.MemoSearchRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class MemoSearchRepositoryImpl(
    private val memoSearchLocalDataSource: MemoSearchLocalDataSource,
) : MemoSearchRepository {
    override fun search(account: Account, query: String): Flow<PagingData<Memo>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { memoSearchLocalDataSource.search(account.id, query) },
        )

        return pager.flow.mapPagingLatest(MemoLocalEntity::toEntity)
    }
}




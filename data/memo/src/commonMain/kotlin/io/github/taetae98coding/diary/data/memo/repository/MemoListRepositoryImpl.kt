package io.github.taetae98coding.diary.data.memo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.MemoListLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.memo.repository.MemoListRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class MemoListRepositoryImpl(
    private val memoListLocalDataSource: MemoListLocalDataSource,
) : MemoListRepository {
    override fun page(account: Account): Flow<PagingData<Memo>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { memoListLocalDataSource.page(account.id) },
        )

        return pager.flow.mapPagingLatest(MemoLocalEntity::toEntity)
    }
}

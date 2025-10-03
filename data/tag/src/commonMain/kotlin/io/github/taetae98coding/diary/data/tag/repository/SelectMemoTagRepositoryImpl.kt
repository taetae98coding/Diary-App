package io.github.taetae98coding.diary.data.tag.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.SelectMemoTagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.SelectMemoTagLocalEntity
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.SelectMemoTag
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.tag.repository.SelectMemoTagRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class SelectMemoTagRepositoryImpl(
    private val selectMemoTagLocalDataSource: SelectMemoTagLocalDataSource,
) : SelectMemoTagRepository {
    override fun page(account: Account, memoId: Uuid): Flow<PagingData<SelectMemoTag>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { selectMemoTagLocalDataSource.page(account.id, memoId) },
        )

        return pager.flow.mapPagingLatest(SelectMemoTagLocalEntity::toEntity)
    }
}

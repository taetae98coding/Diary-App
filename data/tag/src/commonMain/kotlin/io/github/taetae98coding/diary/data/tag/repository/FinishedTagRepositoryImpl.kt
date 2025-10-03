package io.github.taetae98coding.diary.data.tag.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.FinishedTagLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.TagLocalEntity
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.tag.repository.FinishedTagRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class FinishedTagRepositoryImpl(
    private val finishedTagLocalDataSource: FinishedTagLocalDataSource,
) : FinishedTagRepository {
    override fun page(account: Account): Flow<PagingData<Tag>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { finishedTagLocalDataSource.page(account.id) },
        )

        return pager.flow.mapPagingLatest(TagLocalEntity::toEntity)
    }
}

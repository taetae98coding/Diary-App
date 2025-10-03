package io.github.taetae98coding.diary.data.tag.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.database.datasource.TagMemoLocalDataSource
import io.github.taetae98coding.diary.core.database.entity.MemoLocalEntity
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.tag.repository.TagMemoRepository
import io.github.taetae98coding.diary.library.paging.common.mapPagingLatest
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class TagMemoRepositoryImpl(
    private val localDataSource: TagMemoLocalDataSource,
) : TagMemoRepository {
    override fun page(tagId: Uuid): Flow<PagingData<Memo>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { localDataSource.page(tagId) },
        )

        return pager.flow.mapPagingLatest(MemoLocalEntity::toEntity)
    }
}

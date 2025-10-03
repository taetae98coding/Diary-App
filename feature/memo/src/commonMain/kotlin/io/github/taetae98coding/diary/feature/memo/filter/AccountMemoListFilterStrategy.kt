package io.github.taetae98coding.diary.feature.memo.filter

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.tag.TagFilter
import io.github.taetae98coding.diary.domain.memo.usecase.AddMemoListTagFilterUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.PageMemoListTagFilterUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.RemoveMemoListTagFilterUseCase
import io.github.taetae98coding.diary.presenter.memo.filter.MemoListFilterStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class AccountMemoListFilterStrategy(
    private val pageMemoListTagFilterUseCase: PageMemoListTagFilterUseCase,
    private val addMemoListTagFilterUseCase: AddMemoListTagFilterUseCase,
    private val removeMemoListTagFilterUseCase: RemoveMemoListTagFilterUseCase,
) : MemoListFilterStrategy {
    override fun pageTagFilter(): Flow<Result<PagingData<TagFilter>>> {
        return pageMemoListTagFilterUseCase()
    }

    override suspend fun addTagFilter(tagId: Uuid): Result<Unit> {
        return addMemoListTagFilterUseCase(tagId)
    }

    override suspend fun removeTagFilter(tagId: Uuid): Result<Unit> {
        return removeMemoListTagFilterUseCase(tagId)
    }
}

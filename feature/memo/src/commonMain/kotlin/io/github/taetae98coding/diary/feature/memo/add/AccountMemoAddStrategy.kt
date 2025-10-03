package io.github.taetae98coding.diary.feature.memo.add

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.domain.memo.usecase.AddMemoUseCase
import io.github.taetae98coding.diary.domain.tag.usecase.PageTagUseCase
import io.github.taetae98coding.diary.presenter.memo.add.MemoAddStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class AccountMemoAddStrategy(
    private val pageTagUseCase: PageTagUseCase,
    private val addMemoUseCase: AddMemoUseCase,
) : MemoAddStrategy {
    override fun pageTag(): Flow<Result<PagingData<Tag>>> {
        return pageTagUseCase()
    }

    override suspend fun fetchMemoTag(): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun addMemo(
        detail: MemoDetail,
        primaryTag: Uuid?,
        memoTagIds: Set<Uuid>,
    ): Result<Unit> {
        return addMemoUseCase(detail, primaryTag, memoTagIds)
    }
}

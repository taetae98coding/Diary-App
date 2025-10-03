package io.github.taetae98coding.diary.feature.more.finish

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.domain.memo.usecase.DeleteMemoUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.PageFinishedMemoUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.RestoreMemoUseCase
import io.github.taetae98coding.diary.presenter.memo.finish.FinishedMemoStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class AccountFinishMemoStrategy(
    private val pageFinishedMemoUseCase: PageFinishedMemoUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase,
    private val restoreMemoUseCase: RestoreMemoUseCase,
) : FinishedMemoStrategy {
    override fun page(): Flow<Result<PagingData<Memo>>> {
        return pageFinishedMemoUseCase()
    }

    override suspend fun delete(memoId: Uuid): Result<Unit> {
        return deleteMemoUseCase(memoId)
    }

    override suspend fun restore(memoId: Uuid): Result<Unit> {
        return restoreMemoUseCase(memoId)
    }
}

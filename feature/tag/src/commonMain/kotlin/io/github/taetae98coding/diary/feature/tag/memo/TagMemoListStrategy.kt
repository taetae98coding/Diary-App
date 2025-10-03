package io.github.taetae98coding.diary.feature.tag.memo

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.navigation.parameter.TagId
import io.github.taetae98coding.diary.domain.memo.usecase.DeleteMemoUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.FinishMemoUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.RestartMemoUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.RestoreMemoUseCase
import io.github.taetae98coding.diary.domain.tag.usecase.PageTagMemoUseCase
import io.github.taetae98coding.diary.presenter.memo.list.MemoListStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class TagMemoListStrategy(
    private val tagId: TagId,
    private val pageTagMemoUseCase: PageTagMemoUseCase,
    private val finishMemoUseCase: FinishMemoUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase,
    private val restartMemoUseCase: RestartMemoUseCase,
    private val restoreMemoUseCase: RestoreMemoUseCase,
) : MemoListStrategy {
    override fun pageMemo(): Flow<Result<PagingData<Memo>>> {
        return pageTagMemoUseCase(tagId.value)
    }

    override suspend fun fetch(): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun finishMemo(memoId: Uuid): Result<Unit> {
        return finishMemoUseCase(memoId)
    }

    override suspend fun deleteMemo(memoId: Uuid): Result<Unit> {
        return deleteMemoUseCase(memoId)
    }

    override suspend fun restartMemo(memoId: Uuid): Result<Unit> {
        return restartMemoUseCase(memoId)
    }

    override suspend fun restoreMemo(memoId: Uuid): Result<Unit> {
        return restoreMemoUseCase(memoId)
    }
}

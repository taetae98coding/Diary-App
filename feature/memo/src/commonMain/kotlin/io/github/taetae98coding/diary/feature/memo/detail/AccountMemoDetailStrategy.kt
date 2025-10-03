package io.github.taetae98coding.diary.feature.memo.detail

import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.domain.memo.usecase.DeleteMemoUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.FinishMemoUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.GetMemoUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.RestartMemoUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.UpdateMemoUseCase
import io.github.taetae98coding.diary.presenter.memo.detail.MemoDetailStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class AccountMemoDetailStrategy(
    private val memoId: Uuid,
    private val getMemoUseCase: GetMemoUseCase,
    private val updateMemoUseCase: UpdateMemoUseCase,
    private val finishMemoUseCase: FinishMemoUseCase,
    private val restartMemoUseCase: RestartMemoUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase,
) : MemoDetailStrategy {
    override fun getMemo(): Flow<Result<Memo?>> {
        return getMemoUseCase(memoId)
    }

    override suspend fun fetchMemo(): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun finishMemo(): Result<Unit> {
        return finishMemoUseCase(memoId)
    }

    override suspend fun restartMemo(): Result<Unit> {
        return restartMemoUseCase(memoId)
    }

    override suspend fun deleteMemo(): Result<Unit> {
        return deleteMemoUseCase(memoId)
    }

    override suspend fun updateMemo(detail: MemoDetail): Result<Unit> {
        return updateMemoUseCase(memoId, detail)
    }
}

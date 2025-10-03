package io.github.taetae98coding.diary.feature.buddy.group.memo.detail

import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.DeleteBuddyGroupMemoUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.FinishBuddyGroupMemoUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.RestartBuddyGroupMemoUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.UpdateBuddyGroupMemoUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.FetchMemoUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.GetMemoUseCase
import io.github.taetae98coding.diary.presenter.memo.detail.MemoDetailStrategy
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupMemoDetailStrategy(
    private val buddyGroupId: BuddyGroupMemoDetailViewModel.BuddyGroupId,
    private val memoId: BuddyGroupMemoDetailViewModel.MemoId,
    private val fetchMemoUseCase: FetchMemoUseCase,
    private val getMemoUseCase: GetMemoUseCase,
    private val finishBuddyGroupMemoUseCase: FinishBuddyGroupMemoUseCase,
    private val restartBuddyGroupMemoUseCase: RestartBuddyGroupMemoUseCase,
    private val deleteBuddyGroupMemoUseCase: DeleteBuddyGroupMemoUseCase,
    private val updateBuddyGroupMemoUseCase: UpdateBuddyGroupMemoUseCase,
) : MemoDetailStrategy {
    override fun getMemo(): Flow<Result<Memo?>> {
        return getMemoUseCase(memoId.value)
    }

    override suspend fun fetchMemo(): Result<Unit> {
        return fetchMemoUseCase(memoId.value)
    }

    override suspend fun finishMemo(): Result<Unit> {
        return finishBuddyGroupMemoUseCase(buddyGroupId.value, memoId.value)
    }

    override suspend fun restartMemo(): Result<Unit> {
        return restartBuddyGroupMemoUseCase(buddyGroupId.value, memoId.value)
    }

    override suspend fun deleteMemo(): Result<Unit> {
        return deleteBuddyGroupMemoUseCase(buddyGroupId.value, memoId.value)
    }

    override suspend fun updateMemo(detail: MemoDetail): Result<Unit> {
        return updateBuddyGroupMemoUseCase(buddyGroupId.value, memoId.value, detail)
    }
}

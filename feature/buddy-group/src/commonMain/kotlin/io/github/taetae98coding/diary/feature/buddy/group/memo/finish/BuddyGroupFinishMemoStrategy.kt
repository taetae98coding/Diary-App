package io.github.taetae98coding.diary.feature.buddy.group.memo.finish

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.navigation.parameter.BuddyGroupId
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.DeleteBuddyGroupMemoUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.PageBuddyGroupFinishedMemoUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.RestoreBuddyGroupMemoUseCase
import io.github.taetae98coding.diary.presenter.memo.finish.FinishedMemoStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupFinishMemoStrategy(
    private val buddyGroupId: BuddyGroupId,
    private val pageBuddyGroupFinishedMemoUseCase: PageBuddyGroupFinishedMemoUseCase,
    private val deleteBuddyGroupMemoUseCase: DeleteBuddyGroupMemoUseCase,
    private val restoreBuddyGroupMemoUseCase: RestoreBuddyGroupMemoUseCase,
) : FinishedMemoStrategy {
    override fun page(): Flow<Result<PagingData<Memo>>> {
        return pageBuddyGroupFinishedMemoUseCase(buddyGroupId.value)
    }

    override suspend fun delete(memoId: Uuid): Result<Unit> {
        return deleteBuddyGroupMemoUseCase(buddyGroupId.value, memoId)
    }

    override suspend fun restore(memoId: Uuid): Result<Unit> {
        return restoreBuddyGroupMemoUseCase(buddyGroupId.value, memoId)
    }
}

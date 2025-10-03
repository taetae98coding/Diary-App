package io.github.taetae98coding.diary.feature.buddy.group.tag.memo

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.navigation.parameter.BuddyGroupId
import io.github.taetae98coding.diary.core.navigation.parameter.TagId
import io.github.taetae98coding.diary.domain.buddy.group.usecase.FetchBuddyGroupDiaryUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.DeleteBuddyGroupMemoUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.FinishBuddyGroupMemoUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.RestartBuddyGroupMemoUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.RestoreBuddyGroupMemoUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.tag.PageBuddyGroupTagMemoUseCase
import io.github.taetae98coding.diary.presenter.memo.list.MemoListStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupTagMemoListStrategy(
    private val buddyGroupId: BuddyGroupId,
    private val tagId: TagId,
    private val fetchBuddyGroupDiaryUseCase: FetchBuddyGroupDiaryUseCase,
    private val pageBuddyGroupTagMemoUseCase: PageBuddyGroupTagMemoUseCase,
    private val finishBuddyGroupMemoUseCase: FinishBuddyGroupMemoUseCase,
    private val deleteBuddyGroupMemoUseCase: DeleteBuddyGroupMemoUseCase,
    private val restartBuddyGroupMemoUseCase: RestartBuddyGroupMemoUseCase,
    private val restoreBuddyGroupMemoUseCase: RestoreBuddyGroupMemoUseCase,
) : MemoListStrategy {
    override fun pageMemo(): Flow<Result<PagingData<Memo>>> {
        return pageBuddyGroupTagMemoUseCase(buddyGroupId.value, tagId.value)
    }

    override suspend fun fetch(): Result<Unit> {
        return fetchBuddyGroupDiaryUseCase(buddyGroupId.value)
    }

    override suspend fun finishMemo(memoId: Uuid): Result<Unit> {
        return finishBuddyGroupMemoUseCase(buddyGroupId.value, memoId)
    }

    override suspend fun deleteMemo(memoId: Uuid): Result<Unit> {
        return deleteBuddyGroupMemoUseCase(buddyGroupId.value, memoId)
    }

    override suspend fun restartMemo(memoId: Uuid): Result<Unit> {
        return restartBuddyGroupMemoUseCase(buddyGroupId.value, memoId)
    }

    override suspend fun restoreMemo(memoId: Uuid): Result<Unit> {
        return restoreBuddyGroupMemoUseCase(buddyGroupId.value, memoId)
    }
}

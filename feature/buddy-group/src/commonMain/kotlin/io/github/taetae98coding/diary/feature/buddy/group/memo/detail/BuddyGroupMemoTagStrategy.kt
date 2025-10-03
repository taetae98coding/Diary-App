package io.github.taetae98coding.diary.feature.buddy.group.memo.detail

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.entity.tag.SelectMemoTag
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.PageBuddyGroupSelectMemoTagUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.RemoveBuddyGroupMemoPrimaryTagUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.SelectBuddyGroupMemoPrimaryTagUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.SelectBuddyGroupMemoTagUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.UnselectBuddyGroupMemoTagUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.GetMemoUseCase
import io.github.taetae98coding.diary.presenter.memo.tag.MemoTagStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupMemoTagStrategy(
    private val buddyGroupId: BuddyGroupMemoTagViewModel.BuddyGroupId,
    private val memoId: BuddyGroupMemoTagViewModel.MemoId,
    private val getMemoUseCase: GetMemoUseCase,
    private val pageBuddyGroupSelectMemoTagUseCase: PageBuddyGroupSelectMemoTagUseCase,
    private val selectBuddyGroupMemoPrimaryTagUseCase: SelectBuddyGroupMemoPrimaryTagUseCase,
    private val removeBuddyGroupMemoPrimaryTagUseCase: RemoveBuddyGroupMemoPrimaryTagUseCase,
    private val selectBuddyGroupMemoTagUseCase: SelectBuddyGroupMemoTagUseCase,
    private val unselectBuddyGroupMemoTagUseCase: UnselectBuddyGroupMemoTagUseCase,
) : MemoTagStrategy {
    override fun getMemo(): Flow<Result<Memo?>> {
        return getMemoUseCase(memoId.value)
    }

    override fun pageSelectMemoTag(): Flow<Result<PagingData<SelectMemoTag>>> {
        return pageBuddyGroupSelectMemoTagUseCase(buddyGroupId.value, memoId.value)
    }

    override suspend fun selectPrimaryTag(tagId: Uuid): Result<Unit> {
        return selectBuddyGroupMemoPrimaryTagUseCase(buddyGroupId.value, memoId.value, tagId)
    }

    override suspend fun removePrimaryTag(): Result<Unit> {
        return removeBuddyGroupMemoPrimaryTagUseCase(buddyGroupId.value, memoId.value)
    }

    override suspend fun selectMemoTag(tagId: Uuid): Result<Unit> {
        return selectBuddyGroupMemoTagUseCase(buddyGroupId.value, memoId.value, tagId)
    }

    override suspend fun unselectMemoTag(tagId: Uuid): Result<Unit> {
        return unselectBuddyGroupMemoTagUseCase(buddyGroupId.value, memoId.value, tagId)
    }
}

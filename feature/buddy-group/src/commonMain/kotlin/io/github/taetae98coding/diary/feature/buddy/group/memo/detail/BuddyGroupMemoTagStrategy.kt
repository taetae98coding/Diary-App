package io.github.taetae98coding.diary.feature.buddy.group.memo.detail

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.entity.tag.SelectMemoTag
import io.github.taetae98coding.diary.core.navigation.parameter.BuddyGroupId
import io.github.taetae98coding.diary.core.navigation.parameter.MemoId
import io.github.taetae98coding.diary.domain.buddy.group.usecase.FetchBuddyGroupDiaryUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.AddBuddyGroupMemoTagUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.PageBuddyGroupSelectMemoTagUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.RemoveBuddyGroupMemoPrimaryTagUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.RemoveBuddyGroupMemoTagUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.UpdateBuddyGroupMemoPrimaryTagUseCase
import io.github.taetae98coding.diary.domain.memo.usecase.GetMemoUseCase
import io.github.taetae98coding.diary.presenter.memo.tag.MemoTagStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupMemoTagStrategy(
    private val buddyGroupId: BuddyGroupId,
    private val memoId: MemoId,
    private val getMemoUseCase: GetMemoUseCase,
    private val fetchBuddyGroupDiaryUseCase: FetchBuddyGroupDiaryUseCase,
    private val pageBuddyGroupSelectMemoTagUseCase: PageBuddyGroupSelectMemoTagUseCase,
    private val updateBuddyGroupMemoPrimaryTagUseCase: UpdateBuddyGroupMemoPrimaryTagUseCase,
    private val removeBuddyGroupMemoPrimaryTagUseCase: RemoveBuddyGroupMemoPrimaryTagUseCase,
    private val addBuddyGroupMemoTagUseCase: AddBuddyGroupMemoTagUseCase,
    private val removeBuddyGroupMemoTagUseCase: RemoveBuddyGroupMemoTagUseCase,
) : MemoTagStrategy {
    override fun getMemo(): Flow<Result<Memo?>> {
        return getMemoUseCase(memoId.value)
    }

    override suspend fun fetch(): Result<Unit> {
        return fetchBuddyGroupDiaryUseCase(buddyGroupId.value)
    }

    override fun pageSelectMemoTag(): Flow<Result<PagingData<SelectMemoTag>>> {
        return pageBuddyGroupSelectMemoTagUseCase(buddyGroupId.value, memoId.value)
    }

    override suspend fun selectPrimaryTag(tagId: Uuid): Result<Unit> {
        return updateBuddyGroupMemoPrimaryTagUseCase(buddyGroupId.value, memoId.value, tagId)
    }

    override suspend fun removePrimaryTag(): Result<Unit> {
        return removeBuddyGroupMemoPrimaryTagUseCase(buddyGroupId.value, memoId.value)
    }

    override suspend fun selectMemoTag(tagId: Uuid): Result<Unit> {
        return addBuddyGroupMemoTagUseCase(buddyGroupId.value, memoId.value, tagId)
    }

    override suspend fun unselectMemoTag(tagId: Uuid): Result<Unit> {
        return removeBuddyGroupMemoTagUseCase(buddyGroupId.value, memoId.value, tagId)
    }
}

package io.github.taetae98coding.diary.feature.buddy.group.memo.add

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.navigation.parameter.BuddyGroupId
import io.github.taetae98coding.diary.domain.buddy.group.usecase.FetchBuddyGroupDiaryUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.AddBuddyGroupMemoUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.tag.PageBuddyGroupTagUseCase
import io.github.taetae98coding.diary.presenter.memo.add.MemoAddStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupMemoAddStrategy(
    private val buddyGroupId: BuddyGroupId,
    private val pageBuddyGroupTagUseCase: PageBuddyGroupTagUseCase,
    private val fetchBuddyGroupDiaryUseCase: FetchBuddyGroupDiaryUseCase,
    private val addBuddyGroupMemoUseCase: AddBuddyGroupMemoUseCase,
) : MemoAddStrategy {
    override fun pageTag(): Flow<Result<PagingData<Tag>>> {
        return pageBuddyGroupTagUseCase(buddyGroupId.value)
    }

    override suspend fun fetchMemoTag(): Result<Unit> {
        return fetchBuddyGroupDiaryUseCase(buddyGroupId.value)
    }

    override suspend fun addMemo(
        detail: MemoDetail,
        primaryTag: Uuid?,
        memoTagIds: Set<Uuid>,
    ): Result<Unit> {
        return addBuddyGroupMemoUseCase(buddyGroupId.value, detail, primaryTag, memoTagIds)
    }
}

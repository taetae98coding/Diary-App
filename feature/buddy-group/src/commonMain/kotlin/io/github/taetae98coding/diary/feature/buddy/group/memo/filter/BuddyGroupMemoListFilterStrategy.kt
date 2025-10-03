package io.github.taetae98coding.diary.feature.buddy.group.memo.filter

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.tag.TagFilter
import io.github.taetae98coding.diary.core.navigation.parameter.BuddyGroupId
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.AddBuddyGroupMemoListTagFilterUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.PageBuddyGroupMemoListTagFilterUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.RemoveBuddyGroupMemoListTagFilterUseCase
import io.github.taetae98coding.diary.presenter.memo.filter.MemoListFilterStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupMemoListFilterStrategy(
    private val buddyGroupId: BuddyGroupId,
    private val pageBuddyGroupMemoListTagFilterUseCase: PageBuddyGroupMemoListTagFilterUseCase,
    private val addBuddyGroupMemoListTagFilterUseCase: AddBuddyGroupMemoListTagFilterUseCase,
    private val removeBuddyGroupMemoListTagFilterUseCase: RemoveBuddyGroupMemoListTagFilterUseCase,
) : MemoListFilterStrategy {
    override fun pageTagFilter(): Flow<Result<PagingData<TagFilter>>> {
        return pageBuddyGroupMemoListTagFilterUseCase(buddyGroupId.value)
    }

    override suspend fun addTagFilter(tagId: Uuid): Result<Unit> {
        return addBuddyGroupMemoListTagFilterUseCase(buddyGroupId.value, tagId)
    }

    override suspend fun removeTagFilter(tagId: Uuid): Result<Unit> {
        return removeBuddyGroupMemoListTagFilterUseCase(buddyGroupId.value, tagId)
    }
}

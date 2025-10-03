package io.github.taetae98coding.diary.feature.buddy.group.calendar.filter

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.tag.TagFilter
import io.github.taetae98coding.diary.core.navigation.parameter.BuddyGroupId
import io.github.taetae98coding.diary.domain.buddy.group.usecase.calendar.AddBuddyGroupMemoCalendarTagFilterUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.calendar.PageBuddyGroupMemoCalendarTagFilterUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.calendar.RemoveBuddyGroupMemoCalendarTagFilterUseCase
import io.github.taetae98coding.diary.presenter.calendar.filter.CalendarFilterStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupCalendarFilterStrategy(
    private val buddyGroupId: BuddyGroupId,
    private val pageBuddyGroupMemoCalendarTagFilterUseCase: PageBuddyGroupMemoCalendarTagFilterUseCase,
    private val addBuddyGroupMemoCalendarTagFilterUseCase: AddBuddyGroupMemoCalendarTagFilterUseCase,
    private val removeBuddyGroupMemoCalendarTagFilterUseCase: RemoveBuddyGroupMemoCalendarTagFilterUseCase,
) : CalendarFilterStrategy {
    override fun pageTagFilter(): Flow<Result<PagingData<TagFilter>>> {
        return pageBuddyGroupMemoCalendarTagFilterUseCase(buddyGroupId.value)
    }

    override suspend fun addTagFilter(tagId: Uuid): Result<Unit> {
        return addBuddyGroupMemoCalendarTagFilterUseCase(buddyGroupId.value, tagId)
    }

    override suspend fun removeTagFilter(tagId: Uuid): Result<Unit> {
        return removeBuddyGroupMemoCalendarTagFilterUseCase(buddyGroupId.value, tagId)
    }
}

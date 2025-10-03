package io.github.taetae98coding.diary.feature.calendar.filter

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.tag.TagFilter
import io.github.taetae98coding.diary.domain.calendar.usecase.AddMemoCalendarTagFilterUseCase
import io.github.taetae98coding.diary.domain.calendar.usecase.PageMemoCalendarTagFilterUseCase
import io.github.taetae98coding.diary.domain.calendar.usecase.RemoveMemoCalendarTagFilterUseCase
import io.github.taetae98coding.diary.presenter.calendar.filter.CalendarFilterStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class AccountCalendarFilterStrategy(
    private val pageMemoCalendarTagFilterUseCase: PageMemoCalendarTagFilterUseCase,
    private val addMemoCalendarTagFilterUseCase: AddMemoCalendarTagFilterUseCase,
    private val removeMemoCalendarTagFilterUseCase: RemoveMemoCalendarTagFilterUseCase,
) : CalendarFilterStrategy {
    override fun pageTagFilter(): Flow<Result<PagingData<TagFilter>>> {
        return pageMemoCalendarTagFilterUseCase()
    }

    override suspend fun addTagFilter(tagId: Uuid): Result<Unit> {
        return addMemoCalendarTagFilterUseCase(tagId)
    }

    override suspend fun removeTagFilter(tagId: Uuid): Result<Unit> {
        return removeMemoCalendarTagFilterUseCase(tagId)
    }
}

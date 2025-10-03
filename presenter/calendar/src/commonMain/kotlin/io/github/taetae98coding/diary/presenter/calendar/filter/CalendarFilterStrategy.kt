package io.github.taetae98coding.diary.presenter.calendar.filter

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.tag.TagFilter
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface CalendarFilterStrategy {

    public fun pageTagFilter(): Flow<Result<PagingData<TagFilter>>>
    public suspend fun addTagFilter(tagId: Uuid): Result<Unit>
    public suspend fun removeTagFilter(tagId: Uuid): Result<Unit>
}

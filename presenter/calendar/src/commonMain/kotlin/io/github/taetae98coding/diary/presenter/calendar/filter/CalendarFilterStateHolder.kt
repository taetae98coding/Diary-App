package io.github.taetae98coding.diary.presenter.calendar.filter

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.tag.TagFilter
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface CalendarFilterStateHolder {
    public val pagingData: Flow<PagingData<TagFilter>>

    public fun addTagFilter(tagId: Uuid)
    public fun removeTagFilter(tagId: Uuid)
}

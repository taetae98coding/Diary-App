package io.github.taetae98coding.diary.domain.buddy.group.repository

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.tag.TagFilter
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface BuddyGroupMemoCalendarTagFilterRepository {
    public suspend fun upsert(buddyGroupId: Uuid, tagId: Uuid, isFilter: Boolean)
    public fun hasFilter(buddyGroupId: Uuid): Flow<Boolean>
    public fun page(buddyGroupId: Uuid): Flow<PagingData<TagFilter>>
}

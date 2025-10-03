package io.github.taetae98coding.diary.domain.calendar.repository

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.MemoTagFilter
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow

public interface CalendarMemoTagFilterTagRepository {
    public suspend fun upsert(tagId: Uuid, isFilter: Boolean)
    public fun hasFilter(account: Account): Flow<Boolean>
    public fun page(account: Account): Flow<PagingData<MemoTagFilter>>
}

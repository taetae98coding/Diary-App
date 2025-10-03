package io.github.taetae98coding.diary.feature.buddy.group.search

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.buddy.Buddy
import kotlinx.coroutines.flow.Flow

internal interface BuddySearchStateHolder {
    val buddySearchPagingData: Flow<PagingData<BuddySearchUiState>>

    fun search(query: String)
    fun selectBuddy(buddy: Buddy)
    fun unselectBuddy(buddy: Buddy)
}

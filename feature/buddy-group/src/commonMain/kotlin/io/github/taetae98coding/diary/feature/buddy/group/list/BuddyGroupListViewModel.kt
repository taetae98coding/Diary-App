package io.github.taetae98coding.diary.feature.buddy.group.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.github.taetae98coding.diary.domain.buddy.group.usecase.PageBuddyGroupUseCase
import io.github.taetae98coding.diary.library.paging.common.loading
import kotlinx.coroutines.flow.mapLatest
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupListViewModel(
    pageBuddyGroupUseCase: PageBuddyGroupUseCase,
) : ViewModel() {
    val pagingData = pageBuddyGroupUseCase().mapLatest { it.getOrNull() ?: PagingData.loading() }
        .cachedIn(viewModelScope)
}

package io.github.taetae98coding.diary.feature.buddy.group.memo.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.taetae98coding.diary.core.navigation.parameter.BuddyGroupId
import io.github.taetae98coding.diary.domain.buddy.group.usecase.memo.HasBuddyGroupMemoListFilterUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupMemoListFilterViewModel(
    buddyGroupId: BuddyGroupId,
    hasBuddyGroupMemoListFilterUseCase: HasBuddyGroupMemoListFilterUseCase,
) : ViewModel() {
    val hasFilter = hasBuddyGroupMemoListFilterUseCase(buddyGroupId.value).mapLatest { it.getOrNull() ?: false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )
}

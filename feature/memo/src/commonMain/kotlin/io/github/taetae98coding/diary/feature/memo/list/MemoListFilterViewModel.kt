package io.github.taetae98coding.diary.feature.memo.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.taetae98coding.diary.domain.memo.usecase.HasListMemoFilterUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class MemoListFilterViewModel(
    hasListMemoFilterUseCase: HasListMemoFilterUseCase,
) : ViewModel() {
    val hasFilter = hasListMemoFilterUseCase().mapLatest { it.getOrNull() ?: false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )
}

package io.github.taetae98coding.diary.feature.buddy.group.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroupDetail
import io.github.taetae98coding.diary.core.logger.Logger
import io.github.taetae98coding.diary.core.navigation.parameter.BuddyGroupId
import io.github.taetae98coding.diary.domain.buddy.group.usecase.FetchBuddyGroupUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.GetBuddyGroupUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.PageBuddyGroupBuddyUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.UpdateBuddyGroupUseCase
import io.github.taetae98coding.diary.library.paging.common.loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupDetailViewModel(
    private val buddyGroupId: BuddyGroupId,
    getBuddyGroupUseCase: GetBuddyGroupUseCase,
    private val fetchBuddyGroupUseCase: FetchBuddyGroupUseCase,
    private val updateBuddyGroupUseCase: UpdateBuddyGroupUseCase,
    pageBuddyGroupBuddyUseCase: PageBuddyGroupBuddyUseCase,
) : ViewModel() {
    private var isInFetchProgress = false

    val detail = getBuddyGroupUseCase(buddyGroupId.value)
        .mapLatest { it.getOrNull() }
        .mapLatest { it?.detail }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    private val _floatingUiState = MutableStateFlow(BuddyGroupDetailFloatingUiState())
    val floatingUiState = _floatingUiState.asStateFlow()

    val buddyPagingData = pageBuddyGroupBuddyUseCase(buddyGroupId.value).mapLatest { it.getOrNull() ?: PagingData.loading() }
        .cachedIn(viewModelScope)

    private val _effect = MutableStateFlow<BuddyGroupDetailEffect>(BuddyGroupDetailEffect.None)
    val effect = _effect.asStateFlow()

    fun fetch() {
        if (isInFetchProgress) return

        viewModelScope.launch {
            isInFetchProgress = true
            fetchBuddyGroupUseCase(buddyGroupId.value)
                .onFailure {
                    Logger.log("[BuddyGroupDetailViewModel] 버디 그룹 패치 실패 : $it")
                    _effect.emit(BuddyGroupDetailEffect.FetchFail)
                }
            isInFetchProgress = false
        }
    }

    fun update(detail: BuddyGroupDetail) {
        if (floatingUiState.value.isInProgress) return

        viewModelScope.launch {
            _floatingUiState.update { it.copy(isInProgress = true) }
            updateBuddyGroupUseCase(buddyGroupId.value, detail)
                .onSuccess { _effect.emit(BuddyGroupDetailEffect.UpdateFinish) }
                .onFailure {
                    Logger.log("[BuddyGroupDetailViewModel] 버디 그룹 업데이트 실패 : $it")
                    _effect.emit(BuddyGroupDetailEffect.UnknownError)
                }
            _floatingUiState.update { it.copy(isInProgress = false) }
        }
    }

    fun clearEffect() {
        viewModelScope.launch {
            _effect.emit(BuddyGroupDetailEffect.None)
        }
    }
}

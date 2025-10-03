package io.github.taetae98coding.diary.feature.buddy.group.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.buddy.Buddy
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroupDetail
import io.github.taetae98coding.diary.core.logger.Logger
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.exception.BuddyEmptyException
import io.github.taetae98coding.diary.domain.buddy.group.exception.BuddyGroupTitleBlankException
import io.github.taetae98coding.diary.domain.buddy.group.usecase.AddBuddyGroupUseCase
import io.github.taetae98coding.diary.feature.buddy.group.search.BuddySearchStateHolder
import io.github.taetae98coding.diary.feature.buddy.group.search.BuddySearchStateHolderImpl
import io.github.taetae98coding.diary.library.paging.common.notLoading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupAddViewModel(
    getAccountUseCase: GetAccountUseCase,
    private val addBuddyGroupUseCase: AddBuddyGroupUseCase,
    private val searchBottomSheetStateHolder: BuddySearchStateHolderImpl,
) : ViewModel(),
    BuddySearchStateHolder by searchBottomSheetStateHolder {
    private val account = getAccountUseCase()
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            replay = 1,
        )

    private val _floatingUiState = MutableStateFlow(BuddyGroupAddFloatingUiState())
    val floatingUiState = _floatingUiState.asStateFlow()

    val buddyPagingData = combine(account, searchBottomSheetStateHolder.selectedBuddySet) { accountResult, selectedBuddySet ->
        val account = accountResult.getOrNull()

        buildList {
            if (account is Account.User) {
                add(
                    Buddy(
                        id = account.id,
                        email = account.email,
                        profileImage = account.profileImage,
                    ),
                )
            }

            addAll(selectedBuddySet)
        }.sortedBy {
            it.email
        }
    }.mapLatest {
        PagingData.notLoading(it)
    }.cachedIn(viewModelScope)

    private val _effect = MutableStateFlow<BuddyGroupAddEffect>(BuddyGroupAddEffect.None)
    val effect = _effect.asStateFlow()

    fun add(detail: BuddyGroupDetail) {
        if (_floatingUiState.value.isInProgress) return

        viewModelScope.launch {
            _floatingUiState.update { it.copy(isInProgress = true) }

            addBuddyGroupUseCase(detail, searchBottomSheetStateHolder.selectedBuddySet.value.map(Buddy::id))
                .onSuccess { _effect.emit(BuddyGroupAddEffect.AddSuccess) }
                .onFailure { throwable ->
                    Logger.log("[BuddyGroupAddViewModel] 버디 그룹 추가 실패 : $throwable")
                    when (throwable) {
                        is BuddyGroupTitleBlankException -> _effect.emit(BuddyGroupAddEffect.TitleBlank)
                        is BuddyEmptyException -> _effect.emit(BuddyGroupAddEffect.BuddyEmpty)
                        else -> _effect.emit(BuddyGroupAddEffect.UnknownError)
                    }
                }

            _floatingUiState.update { it.copy(isInProgress = false) }
        }
    }

    fun clearEffect() {
        viewModelScope.launch {
            _effect.emit(BuddyGroupAddEffect.None)
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchBottomSheetStateHolder.clear()
    }
}

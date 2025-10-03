package io.github.taetae98coding.diary.feature.buddy.group.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.logger.Logger
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupListAccountViewModel(
    getAccountUseCase: GetAccountUseCase,
) : ViewModel() {
    val uiState = getAccountUseCase().mapLatest { result ->
        result.fold(
            onSuccess = { account ->
                when (account) {
                    is Account.Guest -> BuddyGroupListAccountUiState.Guest
                    is Account.User -> BuddyGroupListAccountUiState.User
                }
            },
            onFailure = {
                Logger.log("[BuddyGroupListAccountViewModel] 계정 조회 실패 : $it")
                BuddyGroupListAccountUiState.Loading
            },
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BuddyGroupListAccountUiState.Loading,
    )
}

package io.github.taetae98coding.diary.feature.more.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.logger.Logger
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.credentials.usecase.LogoutUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class MoreAccountViewModel(
    getAccountUseCase: GetAccountUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {
    val uiState = getAccountUseCase().mapLatest { result ->
        result.fold(
            onSuccess = { account ->
                when (account) {
                    is Account.Guest -> MoreAccountUiState.Guest
                    is Account.User -> {
                        MoreAccountUiState.User(
                            email = account.email,
                            profileImage = account.profileImage,
                        )
                    }
                }
            },
            onFailure = {
                Logger.log("[MoreAccountViewModel] 계정 조회 실패 : $it")
                MoreAccountUiState.Loading
            },
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MoreAccountUiState.Loading,
    )

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}

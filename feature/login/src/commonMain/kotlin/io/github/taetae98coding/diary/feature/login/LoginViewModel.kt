package io.github.taetae98coding.diary.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.logger.Logger
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.credentials.usecase.LoginByGoogleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class LoginViewModel(
    getAccountUseCase: GetAccountUseCase,
    private val loginByGoogleUseCase: LoginByGoogleUseCase,
) : ViewModel() {
    private val isInLoginProgress = MutableStateFlow(false)
    private val account = getAccountUseCase()
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            replay = 1,
        )

    val uiState = combine(
        isInLoginProgress,
        account,
    ) { isInLoginProgress, accountResult ->
        accountResult.fold(
            onSuccess = { account ->
                when (account) {
                    is Account.Guest -> LoginUiState.NotLogin(isInLoginProgress)
                    is Account.User -> LoginUiState.Login
                }
            },
            onFailure = {
                Logger.log("[LoginViewModel] 계정 조회 실패 : $it")
                LoginUiState.Loading
            },
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = LoginUiState.Loading,
    )

    private val _effect = MutableStateFlow<LoginEffect>(LoginEffect.None)
    val effect = _effect.asStateFlow()

    fun googleLogin(idToken: String) {
        if (isInLoginProgress.value) return

        viewModelScope.launch {
            isInLoginProgress.emit(true)
            loginByGoogleUseCase(idToken)
                .onFailure {
                    Logger.log("[LoginViewModel] Google Login 실패 : $it")
                    _effect.emit(LoginEffect.Error)
                }
            isInLoginProgress.emit(false)
        }
    }

    fun clearEffect() {
        viewModelScope.launch {
            _effect.emit(LoginEffect.None)
        }
    }
}

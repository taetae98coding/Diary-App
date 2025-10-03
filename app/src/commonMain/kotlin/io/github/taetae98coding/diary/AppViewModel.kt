package io.github.taetae98coding.diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.taetae98coding.diary.core.logger.Logger
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.push.usecase.RegisterFcmPushTokenUseCase
import io.github.taetae98coding.diary.domain.sync.usecase.SyncUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class AppViewModel(
    getAccountUseCase: GetAccountUseCase,
    private val syncUseCase: SyncUseCase,
    private val registerFcmPushTokenUseCase: RegisterFcmPushTokenUseCase,
) : ViewModel() {
    val account = getAccountUseCase().mapLatest { it.getOrNull() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    fun sync() {
        viewModelScope.launch {
            syncUseCase()
        }
    }

    fun registerPushToken() {
        viewModelScope.launch {
            registerFcmPushTokenUseCase()
                .onSuccess { Logger.log("[AppViewModel] FCM 등록 성공") }
                .onFailure { Logger.log("[AppViewModel] FCM 등록 실패 : $it") }
        }
    }
}

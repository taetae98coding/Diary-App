package io.github.taetae98coding.diary.feature.login

internal sealed class LoginUiState {
    data object Loading : LoginUiState()
    data class NotLogin(
        val isInProgress: Boolean,
    ) : LoginUiState()

    data object Login : LoginUiState()
}

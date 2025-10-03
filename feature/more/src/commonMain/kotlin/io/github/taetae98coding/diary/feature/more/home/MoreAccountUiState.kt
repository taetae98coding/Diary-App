package io.github.taetae98coding.diary.feature.more.home

internal sealed class MoreAccountUiState {
    data object Loading : MoreAccountUiState()
    data object Guest : MoreAccountUiState()
    data class User(
        val email: String,
        val profileImage: String?,
    ) : MoreAccountUiState()
}

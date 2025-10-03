package io.github.taetae98coding.diary.feature.buddy.group.list

internal sealed class BuddyGroupListAccountUiState {
    data object Loading : BuddyGroupListAccountUiState()
    data object Guest : BuddyGroupListAccountUiState()
    data object User : BuddyGroupListAccountUiState()
}

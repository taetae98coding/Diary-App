package io.github.taetae98coding.diary.feature.buddy.group.search

import io.github.taetae98coding.diary.core.entity.buddy.Buddy

internal data class BuddySearchUiState(
    val buddy: Buddy,
    val isChecked: Boolean,
)

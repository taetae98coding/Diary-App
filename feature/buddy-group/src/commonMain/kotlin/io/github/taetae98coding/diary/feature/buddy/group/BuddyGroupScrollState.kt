package io.github.taetae98coding.diary.feature.buddy.group

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

public class BuddyGroupScrollState internal constructor() {
    internal var hasToScrollTop by mutableStateOf(false)
        private set

    public fun requestScrollToTop() {
        hasToScrollTop = true
    }

    internal fun onScrollTop() {
        hasToScrollTop = false
    }
}

@Composable
public fun rememberBuddyGroupScrollState(): BuddyGroupScrollState {
    return remember {
        BuddyGroupScrollState()
    }
}

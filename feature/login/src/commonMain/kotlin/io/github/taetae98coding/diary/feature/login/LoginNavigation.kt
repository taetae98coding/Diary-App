package io.github.taetae98coding.diary.feature.login

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import io.github.taetae98coding.diary.core.navigation.login.LoginNavKey

public fun EntryProviderBuilder<NavKey>.loginEntryProvider(
    backStack: NavBackStack<NavKey>,
) {
    entry<LoginNavKey> {
        LoginScreen(
            navigateUp = backStack::removeLastOrNull,
        )
    }
}

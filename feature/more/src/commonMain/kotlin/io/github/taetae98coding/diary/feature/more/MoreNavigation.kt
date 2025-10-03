package io.github.taetae98coding.diary.feature.more

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import io.github.taetae98coding.diary.core.navigation.login.LoginNavKey
import io.github.taetae98coding.diary.core.navigation.more.GoldHolidayNavKey
import io.github.taetae98coding.diary.core.navigation.more.MoreNavKey
import io.github.taetae98coding.diary.feature.more.holiday.GoldHolidayScreen
import io.github.taetae98coding.diary.feature.more.home.MoreScreen

public fun EntryProviderBuilder<NavKey>.moreEntryProvider(
    backStack: NavBackStack<NavKey>,
) {
    entry<MoreNavKey> {
        MoreScreen(
            navigateToLogin = { backStack.add(LoginNavKey) },
            navigateToGoldHoliday = { backStack.add(GoldHolidayNavKey) },
        )
    }

    entry<GoldHolidayNavKey> {
        GoldHolidayScreen(
            onNavigateUp = backStack::removeLastOrNull,
        )
    }
}

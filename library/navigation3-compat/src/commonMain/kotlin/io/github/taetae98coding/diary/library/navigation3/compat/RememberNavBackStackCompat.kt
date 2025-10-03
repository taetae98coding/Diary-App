package io.github.taetae98coding.diary.library.navigation3.compat

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

@Composable
public expect fun rememberCompatNavBackStack(vararg elements: NavKey): NavBackStack<NavKey>

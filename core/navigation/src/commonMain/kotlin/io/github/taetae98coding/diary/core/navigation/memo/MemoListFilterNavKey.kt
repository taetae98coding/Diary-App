package io.github.taetae98coding.diary.core.navigation.memo

import androidx.navigation3.runtime.NavKey
import io.github.taetae98coding.diary.core.navigation.AppNavigationNavKey
import kotlinx.serialization.Serializable

@Serializable
public data object MemoListFilterNavKey : NavKey, AppNavigationNavKey

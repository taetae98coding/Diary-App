package io.github.taetae98coding.diary.core.navigation.memo

import androidx.navigation3.runtime.NavKey
import io.github.taetae98coding.diary.core.navigation.SyncNavKey
import io.github.taetae98coding.diary.core.navigation.TopLevelNavKey
import kotlinx.serialization.Serializable

@Serializable
public data object MemoListNavKey : NavKey, TopLevelNavKey, SyncNavKey

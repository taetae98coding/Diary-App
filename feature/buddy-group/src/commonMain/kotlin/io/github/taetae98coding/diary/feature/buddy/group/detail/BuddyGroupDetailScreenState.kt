package io.github.taetae98coding.diary.feature.buddy.group.detail

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroupDetail
import io.github.taetae98coding.diary.feature.buddy.group.BuddyGroupDetailState
import io.github.taetae98coding.diary.feature.buddy.group.rememberBuddyGroupDetailState

internal data class BuddyGroupDetailScreenState(
    val hostState: SnackbarHostState,
    val detailState: BuddyGroupDetailState,
)

@Composable
internal fun rememberBuddyGroupDetailScreenState(
    detailProvider: () -> BuddyGroupDetail?,
): BuddyGroupDetailScreenState {
    val hostState = remember { SnackbarHostState() }
    val detailState = rememberBuddyGroupDetailState(detailProvider)

    return remember(
        hostState,
        detailState,
    ) {
        BuddyGroupDetailScreenState(
            hostState = hostState,
            detailState = detailState,
        )
    }
}

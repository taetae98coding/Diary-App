package io.github.taetae98coding.diary.feature.buddy.group.add

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import io.github.taetae98coding.diary.feature.buddy.group.BuddyGroupDetailState
import io.github.taetae98coding.diary.feature.buddy.group.rememberBuddyGroupDetailState

internal data class BuddyGroupAddScreenState(
    val hostState: SnackbarHostState,
    val detailState: BuddyGroupDetailState,
) {
    var isBuddySearchDialogVisible by mutableStateOf(false)
        private set

    fun showBuddySearchDialog() {
        isBuddySearchDialogVisible = true
    }

    fun hideBuddySearchDialog() {
        isBuddySearchDialogVisible = false
    }

    companion object {
        fun saver(
            hostState: SnackbarHostState,
            detailState: BuddyGroupDetailState,
        ): Saver<BuddyGroupAddScreenState, Any> {
            return listSaver(
                save = { listOf(it.isBuddySearchDialogVisible) },
                restore = {
                    BuddyGroupAddScreenState(
                        hostState = hostState,
                        detailState = detailState,
                    ).apply {
                        isBuddySearchDialogVisible = it[0]
                    }
                },
            )
        }
    }
}

@Composable
internal fun rememberBuddyGroupAddScreenState(): BuddyGroupAddScreenState {
    val hostState = remember { SnackbarHostState() }
    val detailState = rememberBuddyGroupDetailState()

    return rememberSaveable(
        inputs = arrayOf(hostState, detailState),
        saver = BuddyGroupAddScreenState.saver(
            hostState = hostState,
            detailState = detailState,
        ),
    ) {
        BuddyGroupAddScreenState(
            hostState = hostState,
            detailState = detailState,
        )
    }
}

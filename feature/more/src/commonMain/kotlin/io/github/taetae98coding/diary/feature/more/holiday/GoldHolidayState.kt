package io.github.taetae98coding.diary.feature.more.holiday

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

internal class GoldHolidayState(
    val pagerState: PagerState,
) {
    val year: Int
        get() = pagerState.currentPage

    var annualLeave by mutableStateOf(0)
        private set

    var isSettingDialogVisible by mutableStateOf(false)
        private set

    suspend fun prevMonth() {
        pagerState.animateScrollToPage(pagerState.currentPage - 1)
    }

    suspend fun nextMonth() {
        pagerState.animateScrollToPage(pagerState.currentPage + 1)
    }

    fun minusAnnualLeave() {
        annualLeave = maxOf(annualLeave - 1, 0)
    }

    fun plusAnnualLeave() {
        annualLeave++
    }

    fun showSetting() {
        isSettingDialogVisible = true
    }

    fun hideSetting() {
        isSettingDialogVisible = false
    }

    companion object {
        fun saver(
            pagerState: PagerState,
        ): Saver<GoldHolidayState, Any> {
            return listSaver(
                save = {
                    listOf(
                        it.annualLeave,
                        it.isSettingDialogVisible,
                    )
                },
                restore = {
                    GoldHolidayState(pagerState = pagerState).apply {
                        annualLeave = it[0] as Int
                        isSettingDialogVisible = it[1] as Boolean
                    }
                },
            )
        }
    }
}

@Composable
internal fun rememberGoldHolidayScreenState(): GoldHolidayState {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val pagerState = rememberPagerState(today.year) { Int.MAX_VALUE }

    return rememberSaveable(
        inputs = arrayOf(pagerState),
        saver = GoldHolidayState.saver(pagerState),
    ) {
        GoldHolidayState(
            pagerState = pagerState,
        )
    }
}

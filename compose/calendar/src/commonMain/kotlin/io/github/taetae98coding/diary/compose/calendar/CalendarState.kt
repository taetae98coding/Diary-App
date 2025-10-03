package io.github.taetae98coding.diary.compose.calendar

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.taetae98coding.diary.compose.calendar.internal.page
import io.github.taetae98coding.diary.compose.calendar.internal.yearMonth
import kotlin.time.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.minusMonth
import kotlinx.datetime.plusMonth
import kotlinx.datetime.todayIn
import kotlinx.datetime.yearMonth

public data class CalendarState(
    internal val pagerState: PagerState,
    internal val selectState: CalendarSelectState,
    internal val startDayOfWeek: DayOfWeek,
) {
    public val yearMonth: YearMonth
        get() = yearMonth(pagerState.currentPage)

    public suspend fun animateScrollTo(yearMonth: YearMonth) {
        pagerState.animateScrollToPage(page(yearMonth))
    }

    public suspend fun animateScrollToPreviousPage() {
        animateScrollTo(yearMonth.minusMonth())
    }

    public suspend fun animateScrollToNextPage() {
        animateScrollTo(yearMonth.plusMonth())
    }
}

@Composable
public fun rememberCalendarState(
    initialYearMonth: YearMonth = Clock.System.todayIn(TimeZone.currentSystemDefault()).yearMonth,
    startDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY,
): CalendarState {
    val pagerState = rememberPagerState(initialPage = page(initialYearMonth)) { Int.MAX_VALUE }
    val selectState = rememberCalendarSelectState()

    return remember(
        pagerState,
        selectState,
        startDayOfWeek,
    ) {
        CalendarState(
            pagerState = pagerState,
            selectState = selectState,
            startDayOfWeek = startDayOfWeek,
        )
    }
}

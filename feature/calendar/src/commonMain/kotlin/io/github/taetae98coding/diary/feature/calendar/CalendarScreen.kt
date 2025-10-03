package io.github.taetae98coding.diary.feature.calendar

import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.compose.core.icon.TagIcon
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.library.lifecycle.common.ifResumed
import io.github.taetae98coding.diary.presenter.calendar.colortext.MemoKey
import io.github.taetae98coding.diary.presenter.calendar.scaffold.CalendarScaffold
import io.github.taetae98coding.diary.presenter.calendar.scaffold.CalendarScaffoldState
import io.github.taetae98coding.diary.presenter.calendar.scaffold.rememberCalendarScaffoldState
import kotlin.uuid.Uuid
import kotlinx.datetime.LocalDateRange
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CalendarScreen(
    scrollState: CalendarScrollState,
    navigateToCalendarFilter: () -> Unit,
    navigateToMemoAdd: (LocalDateRange) -> Unit,
    navigateToMemoDetail: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    calendarViewModel: CalendarViewModel = koinViewModel(),
    filterViewModel: CalendarFilterViewModel = koinViewModel(),
) {
    val state = rememberCalendarScaffoldState()
    val lifecycleOwner = LocalLifecycleOwner.current

    CalendarScaffold(
        colorTestStateHolder = calendarViewModel,
        weatherStateHolder = calendarViewModel,
        holidayStateHolder = calendarViewModel,
        onDrag = { lifecycleOwner.ifResumed { navigateToMemoAdd(it) } },
        onColorTextClick = { key ->
            when (key) {
                is MemoKey -> lifecycleOwner.ifResumed { navigateToMemoDetail(key.memoId) }
            }
        },
        modifier = modifier,
        state = state,
        actions = {
            val hasFilter by filterViewModel.hasFilter.collectAsStateWithLifecycle()

            IconButton(
                onClick = dropUnlessResumed { navigateToCalendarFilter() },
                colors = if (hasFilter) {
                    IconButtonDefaults.iconButtonColors(contentColor = DiaryTheme.colorScheme.primary)
                } else {
                    IconButtonDefaults.iconButtonColors()
                },
            ) {
                TagIcon()
            }
        },
    )

    ScrollEffect(
        state = state,
        scrollState = scrollState,
    )
}

@Composable
private fun ScrollEffect(
    state: CalendarScaffoldState,
    scrollState: CalendarScrollState,
) {
    LaunchedEffect(
        state,
        scrollState.hasToScrollTop,
    ) {
        if (scrollState.hasToScrollTop) {
            state.animateScrollToToday()
            scrollState.onScrollTop()
        }
    }
}

package io.github.taetae98coding.diary.feature.buddy.group.calendar

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.compose.core.icon.NavigateUpIcon
import io.github.taetae98coding.diary.library.lifecycle.common.ifResumed
import io.github.taetae98coding.diary.presenter.calendar.colortext.MemoKey
import io.github.taetae98coding.diary.presenter.calendar.scaffold.CalendarScaffold
import kotlin.uuid.Uuid
import kotlinx.datetime.LocalDateRange
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BuddyGroupCalendarScreen(
    navigateUp: () -> Unit,
    navigateToBuddyGroupMemoAdd: (LocalDateRange) -> Unit,
    navigateToBuddyGroupMemoDetail: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
    calendarViewModel: BuddyGroupCalendarViewModel = koinViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    CalendarScaffold(
        colorTestStateHolder = calendarViewModel,
        weatherStateHolder = calendarViewModel,
        holidayStateHolder = calendarViewModel,
        modifier = modifier,
        onDrag = { lifecycleOwner.ifResumed { navigateToBuddyGroupMemoAdd(it) } },
        onColorTextClick = {
            when (it) {
                is MemoKey -> lifecycleOwner.ifResumed { navigateToBuddyGroupMemoDetail(it.memoId) }
            }
        },
        navigationIcon = {
            IconButton(onClick = dropUnlessResumed { navigateUp() }) {
                NavigateUpIcon()
            }
        },
    )
}

package io.github.taetae98coding.diary.presenter.calendar.topbar

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import io.github.taetae98coding.diary.compose.core.icon.DropDownIcon
import io.github.taetae98coding.diary.compose.core.icon.DropUpIcon
import io.github.taetae98coding.diary.presenter.calendar.button.TodayButton
import io.github.taetae98coding.diary.presenter.calendar.scaffold.CalendarScaffoldState
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number

@Composable
internal fun CalendarTopBar(
    state: CalendarScaffoldState,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier.clip(CircleShape)
                    .clickable { state.showDatePicker() }
                    .padding(ButtonDefaults.TextButtonContentPadding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = state.calendarState.yearMonth.let { "${it.year}년 ${it.month.number}월" })
                Crossfade(state.isDatePickerVisible) { isVisible ->
                    if (isVisible) {
                        DropUpIcon()
                    } else {
                        DropDownIcon()
                    }
                }
            }
        },
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = {
            actions()

            TodayButton(
                todayProvider = { state.today },
                onClick = { coroutineScope.launch { state.animateScrollToToday() } },
            )
        },
    )
}

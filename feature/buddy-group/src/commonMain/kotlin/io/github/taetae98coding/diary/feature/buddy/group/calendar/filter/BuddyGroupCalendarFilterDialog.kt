package io.github.taetae98coding.diary.feature.buddy.group.calendar.filter

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.taetae98coding.diary.presenter.calendar.filter.CalendarFilterDialog
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BuddyGroupCalendarFilterDialog(
    navigateUp: () -> Unit,
    navigateToBuddyGroupTagAdd: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BuddyGroupCalendarFilterViewModel = koinViewModel(),
) {
    CalendarFilterDialog(
        stateHolder = viewModel,
        onDismissRequest = navigateUp,
        onAddTag = navigateToBuddyGroupTagAdd,
        modifier = modifier,
    )
}

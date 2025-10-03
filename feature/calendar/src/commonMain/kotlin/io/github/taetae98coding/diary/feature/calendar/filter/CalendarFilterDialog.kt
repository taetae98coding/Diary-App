package io.github.taetae98coding.diary.feature.calendar.filter

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.taetae98coding.diary.presenter.calendar.filter.CalendarFilterDialog
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CalendarFilterDialog(
    navigateUp: () -> Unit,
    navigateToTagAdd: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CalendarMemoTagFilterViewModel = koinViewModel(),
) {
    CalendarFilterDialog(
        stateHolder = viewModel,
        onDismissRequest = navigateUp,
        onAddTag = navigateToTagAdd,
        modifier = modifier,
    )
}

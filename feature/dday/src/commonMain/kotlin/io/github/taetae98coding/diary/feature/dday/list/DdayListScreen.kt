package io.github.taetae98coding.diary.feature.dday.list

import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.compose.core.button.FloatingAddButton
import io.github.taetae98coding.diary.compose.core.icon.NavigateUpIcon

@Composable
internal fun DdayListScreen(
    navigateUp: () -> Unit,
    navigateToDdayAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopBar(onNavigateUp = dropUnlessResumed { navigateUp }) },
        floatingActionButton = { FloatingAddButton(onClick = dropUnlessResumed { navigateToDdayAdd() }) },
    ) {
        // TODO: D-Day 목록 구현
    }
}

@Composable
private fun TopBar(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = "디데이") },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                NavigateUpIcon()
            }
        },
    )
}

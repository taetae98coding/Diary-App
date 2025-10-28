package io.github.taetae98coding.diary.feature.dday.add

import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.taetae98coding.diary.compose.core.icon.NavigateUpIcon

@Composable
internal fun DdayAddScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                onNavigateUp = onNavigateUp,
            )
        },
    ) {
        // TODO: D-Day 추가 구현
    }
}

@Composable
private fun TopBar(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(text = "디데이 추가")
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                NavigateUpIcon()
            }
        },
    )
}

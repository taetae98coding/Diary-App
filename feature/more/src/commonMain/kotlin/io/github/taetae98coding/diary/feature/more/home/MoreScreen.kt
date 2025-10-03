package io.github.taetae98coding.diary.feature.more.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.library.compose.foundation.shortcutFocus
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MoreScreen(
    navigateToLogin: () -> Unit,
    navigateToGoldHoliday: () -> Unit,
    modifier: Modifier = Modifier,
    accountViewModel: MoreAccountViewModel = koinViewModel(),
) {
    val focusRequester = remember { FocusRequester() }
    val accountCardUiState by accountViewModel.uiState.collectAsStateWithLifecycle()

    MoreScreen(
        accountCardUiStateProvider = { accountCardUiState },
        onLogin = dropUnlessResumed { navigateToLogin() },
        onLogout = accountViewModel::logout,
        onGoldHoliday = dropUnlessResumed { navigateToGoldHoliday() },
        modifier = modifier.shortcutFocus(focusRequester),
    )

    ShortcutEffect(focusRequester = focusRequester)
}

@Composable
internal fun MoreScreen(
    accountCardUiStateProvider: () -> MoreAccountUiState,
    onLogin: () -> Unit,
    onLogout: () -> Unit,
    onGoldHoliday: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopBar() },
    ) {
        Content(
            accountCardUiStateProvider = accountCardUiStateProvider,
            onLogin = onLogin,
            onLogout = onLogout,
            onGoldHoliday = onGoldHoliday,
            modifier = Modifier.fillMaxSize()
                .padding(it),
        )
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = "더보기") },
        modifier = modifier,
    )
}

@Composable
private fun Content(
    accountCardUiStateProvider: () -> MoreAccountUiState,
    onLogin: () -> Unit,
    onLogout: () -> Unit,
    onGoldHoliday: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = DiaryTheme.dimens.screenPaddingValues,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
            MoreAccountCard(
                uiStateProvider = accountCardUiStateProvider,
                onLogin = onLogin,
                onLogout = onLogout,
            )
        }

        item {
            MoreHolidayCard(
                onClick = onGoldHoliday,
            )
        }
    }
}

@Composable
private fun ShortcutEffect(
    focusRequester: FocusRequester,
) {
    LifecycleResumeEffect(focusRequester) {
        focusRequester.requestFocus()
        onPauseOrDispose { }
    }
}

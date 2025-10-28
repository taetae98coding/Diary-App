package io.github.taetae98coding.diary.feature.more.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.style.TextAlign
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
    navigateToFinishMemo: () -> Unit,
    navigateToFinishTag: () -> Unit,
    navigateToGoldHoliday: () -> Unit,
    navigateToDday: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MoreAccountViewModel = koinViewModel(),
) {
    val focusRequester = remember { FocusRequester() }
    val accountCardUiState by viewModel.uiState.collectAsStateWithLifecycle()

    MoreScreen(
        accountCardUiStateProvider = { accountCardUiState },
        onLogin = dropUnlessResumed { navigateToLogin() },
        onLogout = viewModel::logout,
        onFinishMemo = dropUnlessResumed { navigateToFinishMemo() },
        onFinishTag = dropUnlessResumed { navigateToFinishTag() },
        onGoldHoliday = dropUnlessResumed { navigateToGoldHoliday() },
        onDday = dropUnlessResumed { navigateToDday() },
        modifier = modifier.shortcutFocus(focusRequester),
    )

    ShortcutEffect(focusRequester = focusRequester)
}

@Composable
internal fun MoreScreen(
    accountCardUiStateProvider: () -> MoreAccountUiState,
    onLogin: () -> Unit,
    onLogout: () -> Unit,
    onFinishMemo: () -> Unit,
    onFinishTag: () -> Unit,
    onGoldHoliday: () -> Unit,
    onDday: () -> Unit,
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
            onFinishMemo = onFinishMemo,
            onFinishTag = onFinishTag,
            onGoldHoliday = onGoldHoliday,
            onDday = onDday,
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
    onFinishMemo: () -> Unit,
    onFinishTag: () -> Unit,
    onGoldHoliday: () -> Unit,
    onDday: () -> Unit,
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
            DefaultCard(onClick = onFinishMemo) {
                Text(
                    text = "완료된 메모",
                    style = DiaryTheme.typography.titleLargeEmphasized,
                )
            }
        }

        item {
            DefaultCard(onClick = onFinishTag) {
                Text(
                    text = "완료된 태그",
                    style = DiaryTheme.typography.titleLargeEmphasized,
                )
            }
        }

        item {
            DefaultCard(onClick = onGoldHoliday) {
                Text(
                    text = "😎\n황금연휴 찾기",
                    textAlign = TextAlign.Center,
                    style = DiaryTheme.typography.titleLargeEmphasized,
                )
            }
        }

        item {
            DefaultCard(onClick = onDday) {
                Text(
                    text = "📅\n디데이",
                    textAlign = TextAlign.Center,
                    style = DiaryTheme.typography.titleLargeEmphasized,
                )
            }
        }
    }
}

@Composable
private fun DefaultCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.Center,
        ) {
            CompositionLocalProvider(
                LocalTextStyle provides DiaryTheme.typography.titleLargeEmphasized,
            ) {
                content()
            }
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

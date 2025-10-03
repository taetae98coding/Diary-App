package io.github.taetae98coding.diary.feature.login

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import io.github.taetae98coding.diary.compose.core.icon.AppleIcon
import io.github.taetae98coding.diary.compose.core.icon.GoogleIcon
import io.github.taetae98coding.diary.compose.core.icon.NavigateUpIcon
import io.github.taetae98coding.diary.compose.core.snackbar.showSnackbarImmediate
import io.github.taetae98coding.diary.core.logger.Logger
import io.github.taetae98coding.diary.library.google.credentials.compose.rememberGoogleCredentialsManager
import io.github.taetae98coding.diary.library.google.credentials.exception.GoogleCredentialCancellationException
import io.github.taetae98coding.diary.library.google.credentials.exception.GoogleCredentialNotExistException
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun LoginScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val hostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        hostState = hostState,
        uiStateProvider = { uiState },
        onNavigateUp = dropUnlessResumed { navigateUp() },
        onGoogleLogin = viewModel::googleLogin,
        modifier = modifier,
    )

    LoginScreenEffect(
        hostState = hostState,
        viewModel = viewModel,
    )

    NavigateUpEffect(
        uiStateProvider = { uiState },
        navigateUp = navigateUp,
    )
}

@Composable
internal fun LoginScreen(
    hostState: SnackbarHostState,
    uiStateProvider: () -> LoginUiState,
    onNavigateUp: () -> Unit,
    onGoogleLogin: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopBar(onNavigateUp = onNavigateUp) },
        snackbarHost = { SnackbarHost(hostState = hostState) },
    ) { paddingValues ->
        val contentModifier = Modifier.fillMaxSize()

        when (val uiState = uiStateProvider()) {
            is LoginUiState.NotLogin -> {
                Crossfade(
                    targetState = uiState.isInProgress,
                    modifier = Modifier.fillMaxSize()
                        .padding(paddingValues),
                ) { isInProgress ->
                    if (isInProgress) {
                        Loading(modifier = contentModifier)
                    } else {
                        LoginButtonFlow(
                            hostState = hostState,
                            onGoogleLogin = onGoogleLogin,
                            modifier = contentModifier,
                        )
                    }
                }
            }

            is LoginUiState.Loading, is LoginUiState.Login -> Unit
        }
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        ContainedLoadingIndicator()
    }
}

@Composable
private fun LoginButtonFlow(
    hostState: SnackbarHostState,
    onGoogleLogin: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val googleCredentialsManager = rememberGoogleCredentialsManager()

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center,
    ) {
        IconButton(
            onClick = {
                coroutineScope.launch {
                    runCatching { googleCredentialsManager.getIdToken() }
                        .onSuccess { onGoogleLogin(it) }
                        .onFailure { throwable ->
                            Logger.log("[LoginScreen] 구글 로그인 실패 : $throwable")
                            when (throwable) {
                                is GoogleCredentialCancellationException -> Unit
                                is GoogleCredentialNotExistException -> hostState.showSnackbarImmediate("네트워크 상태 또는 구글 계정을 확인하세요")
                                else -> hostState.showSnackbarImmediate("로그인 실패")
                            }
                        }
                }
            },
        ) {
            GoogleIcon()
        }
        IconButton(onClick = {}) {
            AppleIcon()
        }
    }
}

@Composable
private fun TopBar(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = "로그인") },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                NavigateUpIcon()
            }
        },
    )
}

@Composable
private fun LoginScreenEffect(
    hostState: SnackbarHostState,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val effect by viewModel.effect.collectAsStateWithLifecycle()

    LaunchedEffect(hostState, viewModel, effect) {
        when (effect) {
            is LoginEffect.Error -> {
                coroutineScope.launch { hostState.showSnackbarImmediate("네트워크 상태를 확인하세요") }
                viewModel.clearEffect()
            }

            LoginEffect.None -> Unit
        }
    }
}

@Composable
private fun NavigateUpEffect(
    uiStateProvider: () -> LoginUiState,
    navigateUp: () -> Unit,
) {
    val uiState = uiStateProvider()

    LaunchedEffect(uiState) {
        if (uiState == LoginUiState.Login) {
            navigateUp()
        }
    }
}

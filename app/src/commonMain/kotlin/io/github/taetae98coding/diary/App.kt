package io.github.taetae98coding.diary

import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.util.DebugLogger
import io.github.taetae98coding.diary.compose.core.effect.PlatformRefreshLifecycleEffect
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                add(KtorNetworkFetcherFactory())
            }
            .logger(DebugLogger())
            .build()
    }

    DiaryTheme {
        AppScaffold(
            state = rememberAppState(),
            modifier = Modifier.imePadding(),
        )
    }

    SyncEffect()
    RegisterPushTokenEffect()
}

@Composable
private fun SyncEffect(
    viewModel: AppViewModel = koinViewModel(),
) {
    val account by viewModel.account.collectAsStateWithLifecycle()

    PlatformRefreshLifecycleEffect(viewModel, account) {
        viewModel.sync()
    }
}

@Composable
private fun RegisterPushTokenEffect(
    viewModel: AppViewModel = koinViewModel(),
) {
    PlatformRefreshLifecycleEffect(viewModel) {
        viewModel.registerPushToken()
    }
}

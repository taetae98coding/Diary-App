package io.github.taetae98coding.diary.feature.more.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.taetae98coding.diary.compose.core.chip.DiaryAssistChip
import io.github.taetae98coding.diary.compose.core.icon.LoginIcon
import io.github.taetae98coding.diary.compose.core.icon.LogoutIcon
import io.github.taetae98coding.diary.compose.core.image.ProfileImage
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme

@Composable
internal fun MoreAccountCard(
    uiStateProvider: () -> MoreAccountUiState,
    onLogin: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        val contentModifier = Modifier.padding(8.dp)

        Crossfade(
            targetState = uiStateProvider(),
        ) { uiState ->
            when (uiState) {
                is MoreAccountUiState.Loading -> {
                    LoadingContent(
                        uiState = uiState,
                        modifier = contentModifier,
                    )
                }

                is MoreAccountUiState.Guest -> {
                    GuestContent(
                        uiState = uiState,
                        onLogin = onLogin,
                        modifier = contentModifier,
                    )
                }

                is MoreAccountUiState.User -> {
                    AccountContent(
                        uiState = uiState,
                        onLogout = onLogout,
                        modifier = contentModifier,
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingContent(
    uiState: MoreAccountUiState.Loading,
    modifier: Modifier = Modifier,
) {
    ProfileRow(
        uiState = uiState,
        modifier = modifier,
    )
}

@Composable
private fun GuestContent(
    uiState: MoreAccountUiState.Guest,
    onLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ProfileRow(uiState = uiState)
        DiaryAssistChip(
            onClick = onLogin,
            label = { Text(text = "로그인") },
            leadingIcon = { LoginIcon() },
        )
    }
}

@Composable
private fun AccountContent(
    uiState: MoreAccountUiState.User,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ProfileRow(uiState = uiState)
        DiaryAssistChip(
            onClick = onLogout,
            label = { Text(text = "로그아웃") },
            leadingIcon = { LogoutIcon() },
        )
    }
}

@Composable
private fun ProfileRow(
    uiState: MoreAccountUiState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val profileImage = when (uiState) {
            is MoreAccountUiState.User -> uiState.profileImage
            is MoreAccountUiState.Guest -> null
            is MoreAccountUiState.Loading -> null
        }

        val email = when (uiState) {
            is MoreAccountUiState.User -> uiState.email
            is MoreAccountUiState.Guest -> "게스트"
            is MoreAccountUiState.Loading -> null
        }

        ProfileImage(
            model = profileImage,
            modifier = Modifier.size(48.dp),
        )

        ProfileEmail(email = email.orEmpty())
    }
}

@Composable
private fun ProfileEmail(
    email: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = email,
        modifier = modifier,
        style = DiaryTheme.typography.titleLarge,
    )
}

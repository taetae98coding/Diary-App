package io.github.taetae98coding.diary.feature.buddy.group.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme

@Composable
internal fun BuddyGroupListGuestContent(
    onLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "로그인이 필요한 기능입니다.",
                style = DiaryTheme.typography.titleMedium,
            )
            Button(onClick = onLogin) {
                Text(text = "로그인")
            }
        }
    }
}

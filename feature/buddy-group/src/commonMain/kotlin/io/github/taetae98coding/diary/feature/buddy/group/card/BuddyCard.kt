package io.github.taetae98coding.diary.feature.buddy.group.card

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import io.github.taetae98coding.diary.compose.core.box.LoadingBox
import io.github.taetae98coding.diary.compose.core.icon.ChevronRightIcon
import io.github.taetae98coding.diary.compose.core.image.ProfileImage
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.core.entity.buddy.Buddy
import io.github.taetae98coding.diary.library.paging.compose.isError
import io.github.taetae98coding.diary.library.paging.compose.isLoadingVisible

private enum class BuddyCardUiState {
    Loading,
    Error,
    Content,
}

@Composable
internal fun BuddyCard(
    pagingItems: LazyPagingItems<Buddy>,
    onTitle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        TitleRow(
            onClick = onTitle,
            modifier = Modifier.fillMaxWidth(),
        )
        BuddyFlow(
            pagingItems = pagingItems,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun TitleRow(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickable(onClick = onClick)
            .minimumInteractiveComponentSize()
            .padding(DiaryTheme.dimens.diaryPaddingValues),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "버디",
            style = DiaryTheme.typography.titleMediumEmphasized,
        )
        ChevronRightIcon()
    }
}

@Composable
private fun BuddyFlow(
    pagingItems: LazyPagingItems<Buddy>,
    modifier: Modifier = Modifier,
) {
    val uiState = when {
        pagingItems.isLoadingVisible() -> BuddyCardUiState.Loading
        pagingItems.isError() -> BuddyCardUiState.Error
        else -> BuddyCardUiState.Content
    }

    AnimatedContent(
        targetState = uiState,
        modifier = modifier.heightIn(min = 160.dp),
    ) { value ->
        when (value) {
            BuddyCardUiState.Loading -> {
                LoadingBox()
            }

            BuddyCardUiState.Error -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = "네트워크 상태를 확인하세요")
                    Button(onClick = pagingItems::retry) {
                        Text(text = "재시도")
                    }
                }
            }

            BuddyCardUiState.Content -> {
                FlowRow(
                    modifier = modifier.heightIn(min = 160.dp),
                    horizontalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace, Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace, Alignment.CenterVertically),
                    itemVerticalAlignment = Alignment.CenterVertically,
                ) {
                    repeat(pagingItems.itemCount) {
                        val uiState = if (pagingItems.itemCount > it) {
                            pagingItems[it]
                        } else {
                            null
                        }

                        BuddyChip(uiState = uiState)
                    }
                }
            }
        }
    }
}

@Composable
private fun BuddyChip(
    uiState: Buddy?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.border(
            width = 1.dp,
            color = DiaryTheme.colorScheme.outline,
            shape = CircleShape,
        ).padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileImage(
            model = uiState?.profileImage,
            modifier = Modifier.size(24.dp),
        )
        Text(
            text = uiState?.email.orEmpty(),
            style = DiaryTheme.typography.bodyMedium,
        )
    }
}

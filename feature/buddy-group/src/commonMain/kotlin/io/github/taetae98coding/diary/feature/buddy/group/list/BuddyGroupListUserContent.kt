package io.github.taetae98coding.diary.feature.buddy.group.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import io.github.taetae98coding.diary.compose.core.column.PullToRefreshLazyColumn
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroup
import kotlin.uuid.Uuid

@Composable
internal fun BuddyGroupListUserContent(
    state: BuddyGroupListScreenState,
    pagingItems: LazyPagingItems<BuddyGroup>,
    onBuddyGroup: (Uuid) -> Unit,
    modifier: Modifier = Modifier,
) {
    PullToRefreshLazyColumn(
        pagingItems = pagingItems,
        modifier = modifier,
        state = state.lazyListState,
        empty = {
            Text(
                text = "버디 그룹이 없습니다",
                style = DiaryTheme.typography.titleMedium,
            )
        },
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.id },
            contentType = pagingItems.itemContentType { "BuddyGroup" },
        ) { index ->
            BuddyGroupCard(
                uiState = pagingItems[index],
                onClick = { pagingItems[index]?.let { onBuddyGroup(it.id) } },
                modifier = Modifier.fillParentMaxWidth()
                    .animateItem(),
            )
        }
    }
}

@Composable
private fun BuddyGroupCard(
    uiState: BuddyGroup?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(
            text = uiState?.detail?.title.orEmpty(),
            modifier = Modifier.padding(12.dp),
            style = DiaryTheme.typography.titleMedium,
        )
    }
}

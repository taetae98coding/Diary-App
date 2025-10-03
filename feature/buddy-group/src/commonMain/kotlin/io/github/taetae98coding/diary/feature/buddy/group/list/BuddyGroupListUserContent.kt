package io.github.taetae98coding.diary.feature.buddy.group.list

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import io.github.taetae98coding.diary.compose.core.box.EmptyBox
import io.github.taetae98coding.diary.compose.core.chip.DiaryAssistChip
import io.github.taetae98coding.diary.compose.core.icon.CalendarIcon
import io.github.taetae98coding.diary.compose.core.icon.MemoIcon
import io.github.taetae98coding.diary.compose.core.icon.TagIcon
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroup
import io.github.taetae98coding.diary.library.paging.compose.isEmptyVisible
import io.github.taetae98coding.diary.library.paging.compose.isLoadingVisible
import kotlin.uuid.Uuid

@Composable
internal fun BuddyGroupListUserContent(
    state: BuddyGroupListScreenState,
    pagingItems: LazyPagingItems<BuddyGroup>,
    onBuddyGroup: (Uuid) -> Unit,
    onBuddyGroupMemo: (Uuid) -> Unit = onBuddyGroup,
    onBuddyGroupTag: (Uuid) -> Unit = onBuddyGroup,
    onBuddyGroupCalendar: (Uuid) -> Unit = onBuddyGroup,
    modifier: Modifier = Modifier,
) {
    val isRefreshing by remember { derivedStateOf { pagingItems.isLoadingVisible() } }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { pagingItems.refresh() },
        modifier = modifier,
    ) {
        BuddyGroupLazyColumn(
            state = state,
            pagingItems = pagingItems,
            onBuddyGroup = onBuddyGroup,
            onBuddyGroupMemo = onBuddyGroupMemo,
            onBuddyGroupTag = onBuddyGroupTag,
            onBuddyGroupCalendar = onBuddyGroupCalendar,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun BuddyGroupLazyColumn(
    state: BuddyGroupListScreenState,
    pagingItems: LazyPagingItems<BuddyGroup>,
    onBuddyGroup: (Uuid) -> Unit,
    onBuddyGroupMemo: (Uuid) -> Unit = onBuddyGroup,
    onBuddyGroupTag: (Uuid) -> Unit = onBuddyGroup,
    onBuddyGroupCalendar: (Uuid) -> Unit = onBuddyGroup,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        state = state.lazyListState,
        contentPadding = PaddingValues(DiaryTheme.dimens.itemSpace),
        verticalArrangement = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace),
    ) {
        if (pagingItems.isEmptyVisible()) {
            item(
                key = EmptyBox.KEY,
                contentType = EmptyBox.ContentType,
            ) {
                EmptyBox(
                    modifier = Modifier.fillParentMaxSize()
                        .animateItem(),
                ) {
                    Text(text = "버디 그룹이 없습니다")
                }
            }
        } else {
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey { it.id },
                contentType = pagingItems.itemContentType { "BuddyGroup" },
            ) { index ->
                val uiState = pagingItems[index]

                BuddyGroupCard(
                    uiState = uiState,
                    onClick = { uiState?.let { onBuddyGroup(it.id) } },
                    onMemo = { uiState?.let { onBuddyGroupMemo(it.id) } },
                    onTag = { uiState?.let { onBuddyGroupTag(it.id) } },
                    onCalendar = { uiState?.let { onBuddyGroupCalendar(it.id) } },
                    modifier = Modifier.fillParentMaxWidth()
                        .animateItem(),
                )
            }
        }
    }
}

@Composable
private fun BuddyGroupCard(
    uiState: BuddyGroup?,
    onClick: () -> Unit,
    onMemo: () -> Unit,
    onTag: () -> Unit,
    onCalendar: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(onClick = onClick, modifier = modifier) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = uiState?.detail?.title.orEmpty(),
                modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
                maxLines = 1,
                style = DiaryTheme.typography.titleLargeEmphasized,
            )
            ActionChipRow(
                onMemo = onMemo,
                onTag = onTag,
                onCalendar = onCalendar,
            )
        }
    }
}

@Composable
private fun ActionChipRow(
    onMemo: () -> Unit,
    onTag: () -> Unit,
    onCalendar: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        DiaryAssistChip(
            onClick = onMemo,
            label = { Text(text = "메모") },
            leadingIcon = { MemoIcon() },
            colors = AssistChipDefaults.assistChipColors()
                .copy(leadingIconContentColor = AssistChipDefaults.assistChipColors().labelColor),
        )

        DiaryAssistChip(
            onClick = onTag,
            label = { Text(text = "태그") },
            leadingIcon = { TagIcon() },
            colors = AssistChipDefaults.assistChipColors()
                .copy(leadingIconContentColor = AssistChipDefaults.assistChipColors().labelColor),
        )

        DiaryAssistChip(
            onClick = onCalendar,
            label = { Text(text = "캘린더") },
            leadingIcon = { CalendarIcon() },
            colors = AssistChipDefaults.assistChipColors()
                .copy(leadingIconContentColor = AssistChipDefaults.assistChipColors().labelColor),
        )
    }
}

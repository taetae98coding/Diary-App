package io.github.taetae98coding.diary.compose.core.column

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import io.github.taetae98coding.diary.compose.core.theme.DiaryTheme
import io.github.taetae98coding.diary.library.paging.compose.isEmptyVisible
import io.github.taetae98coding.diary.library.paging.compose.isLoading

@Composable
public fun <T : Any> PullToRefreshLazyColumn(
    pagingItems: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = DiaryTheme.dimens.screenPaddingValues,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(DiaryTheme.dimens.itemSpace),
    empty: @Composable BoxScope.() -> Unit = {},
    content: LazyListScope.() -> Unit,
) {
    val isRefreshing by remember(pagingItems) { derivedStateOf { pagingItems.isLoading() } }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = pagingItems::refresh,
        modifier = modifier,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = state,
            contentPadding = contentPadding,
            verticalArrangement = verticalArrangement,
        ) {
            if (pagingItems.isEmptyVisible()) {
                item(
                    key = "empty",
                    contentType = "empty",
                ) {
                    Box(
                        modifier = Modifier.fillParentMaxSize()
                            .animateItem(),
                        contentAlignment = Alignment.Center,
                    ) {
                        empty()
                    }
                }
            } else {
                content()
            }
        }
    }
}

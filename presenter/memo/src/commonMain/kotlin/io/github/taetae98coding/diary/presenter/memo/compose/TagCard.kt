package io.github.taetae98coding.diary.presenter.memo.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems

@Composable
internal fun <T : Any> TagCard(
    pagingItems: LazyPagingItems<T>,
    title: @Composable () -> Unit,
    tag: @Composable (T?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        title()
        TagFlow(
            pagingItems = pagingItems,
            tag = tag,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

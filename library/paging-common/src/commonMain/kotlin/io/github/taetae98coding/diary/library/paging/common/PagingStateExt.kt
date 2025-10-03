package io.github.taetae98coding.diary.library.paging.common

import androidx.paging.PagingState

public fun PagingState<Int, *>.clippedRefreshKey(): Int? {
    return anchorPosition?.let { maxOf(0, it - (config.initialLoadSize / 2)) }
}

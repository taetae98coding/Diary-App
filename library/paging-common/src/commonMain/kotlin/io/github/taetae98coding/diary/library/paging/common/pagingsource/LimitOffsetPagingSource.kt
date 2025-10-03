package io.github.taetae98coding.diary.library.paging.common.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.taetae98coding.diary.library.paging.common.clippedRefreshKey

public abstract class LimitOffsetPagingSource<T : Any>(
    private val initialOffset: Int = 0,
) : PagingSource<Int, T>() {
    override val jumpingSupported: Boolean = true

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.clippedRefreshKey()
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val key = params.key ?: initialOffset
            val limit = when (params) {
                is LoadParams.Prepend -> minOf(key, params.loadSize)
                else -> params.loadSize
            }
            val offset = when (params) {
                is LoadParams.Prepend -> maxOf(0, key - params.loadSize)
                else -> key
            }
            val page = paging(limit, offset)
            val nextOffset = offset + page.data.size

            LoadResult.Page(
                data = page.data,
                prevKey = offset.takeIf { page.data.isNotEmpty() && it > 0 },
                nextKey = nextOffset.takeIf { page.data.isNotEmpty() && page.data.size >= limit && it < page.count },
                itemsBefore = offset,
                itemsAfter = maxOf(0, page.count - nextOffset),
            )
        } catch (throwable: Throwable) {
            LoadResult.Error(throwable)
        }
    }

    protected abstract suspend fun paging(limit: Int, offset: Int): Page<T>

    public data class Page<T : Any>(
        val count: Int,
        val data: List<T>,
    )
}

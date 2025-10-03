package io.github.taetae98coding.diary.library.paging.common.remotemediator

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import io.github.taetae98coding.diary.library.paging.common.clippedRefreshKey

public abstract class LimitOffsetRemoteMediator<T : Any> : RemoteMediator<Int, T>() {
    private var prependKey = 0
    private var appendKey = 0

    public open val initialLimit: Int = 100
    public open val initialOffset: Int = 0

    override suspend fun initialize(): InitializeAction {
        return try {
            val response = submit(limit = initialLimit, offset = initialOffset)

            prependKey = initialOffset
            appendKey = response.size

            InitializeAction.SKIP_INITIAL_REFRESH
        } catch (_: Throwable) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, T>,
    ): MediatorResult {
        return try {
            when (loadType) {
                LoadType.REFRESH -> refresh(state)
                LoadType.PREPEND -> prepend(state)
                LoadType.APPEND -> append(state)
            }
        } catch (throwable: Throwable) {
            MediatorResult.Error(throwable)
        }
    }

    private suspend fun refresh(state: PagingState<Int, T>): MediatorResult.Success {
        val limit = state.config.initialLoadSize
        val offset = state.clippedRefreshKey() ?: 0
        val response = submit(limit, offset)

        prependKey = offset
        appendKey = offset + response.size

        return MediatorResult.Success(response.size < limit)
    }

    private suspend fun prepend(state: PagingState<Int, T>): MediatorResult.Success {
        val limit = minOf(prependKey, state.config.pageSize)
        val offset = maxOf(0, prependKey - state.config.pageSize)

        if (limit == 0) {
            return MediatorResult.Success(true)
        }

        upsert(limit, offset)
        prependKey = offset

        return MediatorResult.Success(offset <= 0)
    }

    private suspend fun append(state: PagingState<Int, T>): MediatorResult.Success {
        val limit = state.config.pageSize
        val offset = appendKey
        val response = upsert(limit, offset)

        appendKey = offset + response.size

        return MediatorResult.Success(response.size < limit)
    }

    public abstract suspend fun submit(limit: Int, offset: Int): List<T>
    public abstract suspend fun upsert(limit: Int, offset: Int): List<T>
}

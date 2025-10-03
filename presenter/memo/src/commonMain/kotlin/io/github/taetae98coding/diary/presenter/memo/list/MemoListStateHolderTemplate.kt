package io.github.taetae98coding.diary.presenter.memo.list

import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.library.paging.common.loading
import kotlin.uuid.Uuid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

public class MemoListStateHolderTemplate(
    private val strategy: MemoListStrategy,
    private val coroutineScope: CoroutineScope = MainScope(),
) : MemoListStateHolder,
    AutoCloseable {
    private var isInFetchProgress = false

    override val pagingData: Flow<PagingData<Memo>> = strategy.pageMemo()
        .mapLatest { result ->
            result.fold(
                onSuccess = { it },
                onFailure = { PagingData.loading() },
            )
        }.cachedIn(coroutineScope)

    private val _effect = MutableStateFlow<MemoListEffect>(MemoListEffect.None)
    override val effect: StateFlow<MemoListEffect> = _effect.asStateFlow()

    override fun fetch() {
        if (isInFetchProgress) return

        coroutineScope.launch {
            isInFetchProgress = true
            strategy.fetch()
                .onFailure { _effect.emit(MemoListEffect.FetchError) }
            isInFetchProgress = false
        }
    }

    override fun finish(memoId: Uuid) {
        coroutineScope.launch {
            strategy.finishMemo(memoId)
                .onSuccess { _effect.emit(MemoListEffect.FinishSuccess(memoId)) }
                .onFailure { _effect.emit(MemoListEffect.UnknownError) }
        }
    }

    override fun delete(memoId: Uuid) {
        coroutineScope.launch {
            strategy.deleteMemo(memoId)
                .onSuccess { _effect.emit(MemoListEffect.DeleteSuccess(memoId)) }
                .onFailure { _effect.emit(MemoListEffect.UnknownError) }
        }
    }

    override fun restart(memoId: Uuid) {
        coroutineScope.launch {
            strategy.restartMemo(memoId)
                .onFailure { _effect.emit(MemoListEffect.UnknownError) }
        }
    }

    override fun restore(memoId: Uuid) {
        coroutineScope.launch {
            strategy.restoreMemo(memoId)
                .onFailure { _effect.emit(MemoListEffect.UnknownError) }
        }
    }

    override fun clearEffect() {
        coroutineScope.launch {
            _effect.emit(MemoListEffect.None)
        }
    }

    override fun close() {
        coroutineScope.cancel()
    }
}

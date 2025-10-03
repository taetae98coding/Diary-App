package io.github.taetae98coding.diary.presenter.memo.list

import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.github.taetae98coding.diary.core.coroutines.ext.MainImmediateScope
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.logger.Logger
import io.github.taetae98coding.diary.library.paging.common.loading
import kotlin.uuid.Uuid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

public class MemoListStateHolderTemplate(
    private val strategy: MemoListStrategy,
    private val coroutineScope: CoroutineScope = MainImmediateScope(),
) : MemoListStateHolder,
    AutoCloseable {
    private var isInFetchProgress = false

    override val pagingData: Flow<PagingData<Memo>> = strategy.pageMemo()
        .mapLatest { result ->
            result.fold(
                onSuccess = { it },
                onFailure = {
                    Logger.log("[MemoListStateHolderTemplate] 메모 페이지 로드 실패 : $it")
                    PagingData.loading()
                },
            )
        }.cachedIn(coroutineScope)

    private val _effect = MutableStateFlow<MemoListEffect>(MemoListEffect.None)
    override val effect: StateFlow<MemoListEffect> = _effect.asStateFlow()

    override fun fetch() {
        if (isInFetchProgress) return

        coroutineScope.launch {
            isInFetchProgress = true
            strategy.fetch()
                .onFailure {
                    Logger.log("[MemoListStateHolderTemplate] 메모 패치 실패 : $it")
                    _effect.emit(MemoListEffect.FetchError)
                }
            isInFetchProgress = false
        }
    }

    override fun finish(memoId: Uuid) {
        coroutineScope.launch {
            strategy.finishMemo(memoId)
                .onSuccess { _effect.emit(MemoListEffect.FinishSuccess(memoId)) }
                .onFailure {
                    Logger.log("[MemoListStateHolderTemplate] 메모 완료 실패 : $it")
                    _effect.emit(MemoListEffect.UnknownError)
                }
        }
    }

    override fun delete(memoId: Uuid) {
        coroutineScope.launch {
            strategy.deleteMemo(memoId)
                .onSuccess { _effect.emit(MemoListEffect.DeleteSuccess(memoId)) }
                .onFailure {
                    Logger.log("[MemoListStateHolderTemplate] 메모 삭제 실패 : $it")
                    _effect.emit(MemoListEffect.UnknownError)
                }
        }
    }

    override fun restart(memoId: Uuid) {
        coroutineScope.launch {
            strategy.restartMemo(memoId)
                .onFailure {
                    Logger.log("[MemoListStateHolderTemplate] 메모 재시작 실패 : $it")
                    _effect.emit(MemoListEffect.UnknownError)
                }
        }
    }

    override fun restore(memoId: Uuid) {
        coroutineScope.launch {
            strategy.restoreMemo(memoId)
                .onFailure {
                    Logger.log("[MemoListStateHolderTemplate] 메모 복원 실패 : $it")
                    _effect.emit(MemoListEffect.UnknownError)
                }
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

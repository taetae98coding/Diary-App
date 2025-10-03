package io.github.taetae98coding.diary.presenter.memo.finish

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

public class FinishedMemoStateHolderTemplate(
    private val strategy: FinishedMemoStrategy,
    private val coroutineScope: CoroutineScope = MainImmediateScope(),
) : FinishedMemoStateHolder,
    AutoCloseable {
    override val pagingData: Flow<PagingData<Memo>> = strategy.page().mapLatest { it.getOrNull() ?: PagingData.loading() }
        .cachedIn(coroutineScope)

    private val _effect = MutableStateFlow<FinishedMemoEffect>(FinishedMemoEffect.None)
    override val effect: StateFlow<FinishedMemoEffect> = _effect.asStateFlow()

    override fun delete(memoId: Uuid) {
        coroutineScope.launch {
            strategy.delete(memoId)
                .onSuccess { _effect.emit(FinishedMemoEffect.DeleteSuccess(memoId)) }
                .onFailure {
                    Logger.log("[FinishedMemoStateHolderTemplate] 완료 메모 삭제 실패 : $it")
                    _effect.emit(FinishedMemoEffect.UnknownError)
                }
        }
    }

    override fun restore(memoId: Uuid) {
        coroutineScope.launch {
            strategy.restore(memoId)
        }
    }

    override fun clearEffect() {
        coroutineScope.launch { _effect.emit(FinishedMemoEffect.None) }
    }

    override fun close() {
        coroutineScope.cancel()
    }
}

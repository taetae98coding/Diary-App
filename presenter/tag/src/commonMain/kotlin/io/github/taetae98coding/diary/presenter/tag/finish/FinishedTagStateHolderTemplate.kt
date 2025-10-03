package io.github.taetae98coding.diary.presenter.tag.finish

import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.github.taetae98coding.diary.core.coroutines.ext.MainImmediateScope
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.logger.Logger
import kotlin.uuid.Uuid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

public class FinishedTagStateHolderTemplate(
    private val strategy: FinishedTagStrategy,
    private val coroutineScope: CoroutineScope = MainImmediateScope(),
) : FinishedTagStateHolder,
    AutoCloseable {
    override val pagingData: Flow<PagingData<Tag>> = strategy.page().mapLatest { it.getOrNull() ?: PagingData.empty() }
        .cachedIn(coroutineScope)

    private val _effect = MutableStateFlow<FinishedTagEffect>(FinishedTagEffect.None)
    override val effect: StateFlow<FinishedTagEffect> = _effect.asStateFlow()

    override fun delete(tagId: Uuid) {
        coroutineScope.launch {
            strategy.delete(tagId)
                .onSuccess { _effect.emit(FinishedTagEffect.DeleteSuccess(tagId)) }
                .onFailure {
                    Logger.log("[FinishedTagStateHolderTemplate] 완료 태그 삭제 실패 : $it")
                    _effect.emit(FinishedTagEffect.UnknownError)
                }
        }
    }

    override fun restore(tagId: Uuid) {
        coroutineScope.launch {
            strategy.restore(tagId)
        }
    }

    override fun clearEffect() {
        coroutineScope.launch { _effect.emit(FinishedTagEffect.None) }
    }

    override fun close() {
        coroutineScope.cancel()
    }
}

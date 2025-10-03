package io.github.taetae98coding.diary.presenter.tag.list

import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.github.taetae98coding.diary.core.coroutines.ext.MainImmediateScope
import io.github.taetae98coding.diary.core.entity.tag.Tag
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

public class TagListStateHolderTemplate(
    private val strategy: TagListStrategy,
    private val coroutineScope: CoroutineScope = MainImmediateScope(),
) : TagListStateHolder,
    AutoCloseable {
    private var isInFetchProgress = false

    override val pagingData: Flow<PagingData<Tag>> = strategy.pageTag().mapLatest { result ->
        result.fold(
            onSuccess = { it },
            onFailure = {
                Logger.log("[TagListStateHolderTemplate] 태그 페이지 로드 실패 : $it")
                PagingData.loading()
            },
        )
    }.cachedIn(coroutineScope)

    private val _effect = MutableStateFlow<TagListEffect>(TagListEffect.None)
    override val effect: StateFlow<TagListEffect> = _effect.asStateFlow()

    override fun fetch() {
        if (isInFetchProgress) return

        coroutineScope.launch {
            isInFetchProgress = true
            strategy.fetch()
                .onFailure {
                    Logger.log("[TagListStateHolderTemplate] 태그 패치 실패 : $it")
                    _effect.emit(TagListEffect.FetchError)
                }
            isInFetchProgress = false
        }
    }

    override fun finish(tagId: Uuid) {
        coroutineScope.launch {
            strategy.finishTag(tagId)
                .onSuccess { _effect.emit(TagListEffect.FinishSuccess(tagId)) }
                .onFailure {
                    Logger.log("[TagListStateHolderTemplate] 태그 완료 실패 : $it")
                    _effect.emit(TagListEffect.UnknownError)
                }
        }
    }

    override fun delete(tagId: Uuid) {
        coroutineScope.launch {
            strategy.deleteTag(tagId)
                .onSuccess { _effect.emit(TagListEffect.DeleteSuccess(tagId)) }
                .onFailure {
                    Logger.log("[TagListStateHolderTemplate] 태그 삭제 실패 : $it")
                    _effect.emit(TagListEffect.UnknownError)
                }
        }
    }

    override fun restart(tagId: Uuid) {
        coroutineScope.launch {
            strategy.restartTag(tagId)
                .onFailure {
                    Logger.log("[TagListStateHolderTemplate] 태그 재시작 실패 : $it")
                    _effect.emit(TagListEffect.UnknownError)
                }
        }
    }

    override fun restore(tagId: Uuid) {
        coroutineScope.launch {
            strategy.restoreTag(tagId)
                .onFailure {
                    Logger.log("[TagListStateHolderTemplate] 태그 복원 실패 : $it")
                    _effect.emit(TagListEffect.UnknownError)
                }
        }
    }

    override fun clearEffect() {
        coroutineScope.launch {
            _effect.emit(TagListEffect.None)
        }
    }

    override fun close() {
        coroutineScope.cancel()
    }
}

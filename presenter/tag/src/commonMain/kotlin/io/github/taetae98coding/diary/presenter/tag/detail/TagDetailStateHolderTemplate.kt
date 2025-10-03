package io.github.taetae98coding.diary.presenter.tag.detail

import io.github.taetae98coding.diary.core.coroutines.ext.MainImmediateScope
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.core.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

public class TagDetailStateHolderTemplate(
    private val strategy: TagDetailStrategy,
    private val coroutineScope: CoroutineScope = MainImmediateScope(),
) : TagDetailStateHolder,
    AutoCloseable {
    private var isInFetchProgress = false

    private val tag = strategy.getTag()
        .shareIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
        )

    override val detail: StateFlow<TagDetail?> = tag.mapLatest { result ->
        result.fold(
            onSuccess = { it?.detail },
            onFailure = {
                Logger.log("[TagDetailStateHolderTemplate] 태그 상세 조회 실패 : $it")
                null
            },
        )
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    override val finishUiState: StateFlow<TagDetailFinishUiState> = tag.mapLatest { result ->
        result.fold(
            onSuccess = { TagDetailFinishUiState(isFinished = it?.isFinished ?: false) },
            onFailure = {
                Logger.log("[TagDetailStateHolderTemplate] 태그 완료 상태 조회 실패 : $it")
                TagDetailFinishUiState()
            },
        )
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TagDetailFinishUiState(),
    )

    private val _floatingUiState = MutableStateFlow(TagDetailFloatingUiState())
    override val floatingUiState: StateFlow<TagDetailFloatingUiState> = _floatingUiState.asStateFlow()

    private val _effect: MutableStateFlow<TagDetailEffect> = MutableStateFlow(TagDetailEffect.None)
    override val effect: StateFlow<TagDetailEffect> = _effect.asStateFlow()

    override fun fetch() {
        if (isInFetchProgress) return

        coroutineScope.launch {
            isInFetchProgress = true
            strategy.fetch()
                .onFailure {
                    Logger.log("[TagDetailStateHolderTemplate] 태그 패치 실패 : $it")
                    _effect.emit(TagDetailEffect.FetchError)
                }
            isInFetchProgress = false
        }
    }

    override fun finish() {
        coroutineScope.launch {
            strategy.finishTag()
                .onFailure {
                    Logger.log("[TagDetailStateHolderTemplate] 태그 완료 실패 : $it")
                    _effect.emit(TagDetailEffect.UnknownError)
                }
        }
    }

    override fun restart() {
        coroutineScope.launch {
            strategy.restartTag()
                .onFailure {
                    Logger.log("[TagDetailStateHolderTemplate] 태그 재시작 실패 : $it")
                    _effect.emit(TagDetailEffect.UnknownError)
                }
        }
    }

    override fun delete() {
        coroutineScope.launch {
            strategy.deleteTag()
                .onSuccess { _effect.emit(TagDetailEffect.DeleteFinish) }
                .onFailure {
                    Logger.log("[TagDetailStateHolderTemplate] 태그 삭제 실패 : $it")
                    _effect.emit(TagDetailEffect.UnknownError)
                }
        }
    }

    override fun update(detail: TagDetail) {
        if (floatingUiState.value.isInProgress) return

        coroutineScope.launch {
            _floatingUiState.update { it.copy(isInProgress = true) }
            strategy.updateTag(detail)
                .onSuccess { _effect.emit(TagDetailEffect.UpdateFinish) }
                .onFailure {
                    Logger.log("[TagDetailStateHolderTemplate] 태그 업데이트 실패 : $it")
                    _effect.emit(TagDetailEffect.UnknownError)
                }
            _floatingUiState.update { it.copy(isInProgress = false) }
        }
    }

    override fun clearEffect() {
        coroutineScope.launch {
            _effect.emit(TagDetailEffect.None)
        }
    }

    override fun close() {
        coroutineScope.cancel()
    }
}

package io.github.taetae98coding.diary.presenter.memo.detail

import io.github.taetae98coding.diary.core.coroutines.ext.MainImmediateScope
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
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

public class MemoDetailStateHolderTemplate(
    private val strategy: MemoDetailStrategy,
    private val coroutineScope: CoroutineScope = MainImmediateScope(),
) : MemoDetailStateHolder,
    AutoCloseable {
    private var isInFetchProgress = false

    private val memo = strategy.getMemo()
        .shareIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
        )

    override val detail: StateFlow<MemoDetail?> = memo.mapLatest { result ->
        result.fold(
            onSuccess = { it?.detail },
            onFailure = {
                Logger.log("[MemoDetailStateHolderTemplate] 메모 상세 조회 실패 : $it")
                null
            },
        )
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    override val finishUiState: StateFlow<MemoDetailFinishUiState> = memo.mapLatest { result ->
        result.fold(
            onSuccess = { MemoDetailFinishUiState(isFinished = it?.isFinished ?: false) },
            onFailure = {
                Logger.log("[MemoDetailStateHolderTemplate] 메모 완료 상태 조회 실패 : $it")
                MemoDetailFinishUiState()
            },
        )
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MemoDetailFinishUiState(),
    )

    private val _floatingUiState = MutableStateFlow(MemoDetailFloatingUiState())
    override val floatingUiState: StateFlow<MemoDetailFloatingUiState> = _floatingUiState.asStateFlow()

    private val _effect: MutableStateFlow<MemoDetailEffect> = MutableStateFlow(MemoDetailEffect.None)
    override val effect: StateFlow<MemoDetailEffect> = _effect.asStateFlow()
    override fun fetch() {
        if (isInFetchProgress) return

        coroutineScope.launch {
            isInFetchProgress = true
            strategy.fetch()
                .onFailure {
                    Logger.log("[MemoDetailStateHolderTemplate] 메모 상세 패치 실패 : $it")
                    _effect.emit(MemoDetailEffect.FetchError)
                }
            isInFetchProgress = false
        }
    }

    override fun finish() {
        coroutineScope.launch {
            strategy.finishMemo()
                .onFailure {
                    Logger.log("[MemoDetailStateHolderTemplate] 메모 완료 실패 : $it")
                    _effect.emit(MemoDetailEffect.UnknownError)
                }
        }
    }

    override fun restart() {
        coroutineScope.launch {
            strategy.restartMemo()
                .onFailure {
                    Logger.log("[MemoDetailStateHolderTemplate] 메모 재시작 실패 : $it")
                    _effect.emit(MemoDetailEffect.UnknownError)
                }
        }
    }

    override fun delete() {
        coroutineScope.launch {
            strategy.deleteMemo()
                .onSuccess { _effect.emit(MemoDetailEffect.DeleteFinish) }
                .onFailure {
                    Logger.log("[MemoDetailStateHolderTemplate] 메모 삭제 실패 : $it")
                    _effect.emit(MemoDetailEffect.UnknownError)
                }
        }
    }

    override fun update(detail: MemoDetail) {
        if (floatingUiState.value.isInProgress) return

        coroutineScope.launch {
            _floatingUiState.update { it.copy(isInProgress = true) }
            strategy.updateMemo(detail)
                .onSuccess { _effect.emit(MemoDetailEffect.UpdateFinish) }
                .onFailure {
                    Logger.log("[MemoDetailStateHolderTemplate] 메모 업데이트 실패 : $it")
                    _effect.emit(MemoDetailEffect.UnknownError)
                }
            _floatingUiState.update { it.copy(isInProgress = false) }
        }
    }

    override fun clearEffect() {
        coroutineScope.launch {
            _effect.emit(MemoDetailEffect.None)
        }
    }

    override fun close() {
        coroutineScope.cancel()
    }
}
